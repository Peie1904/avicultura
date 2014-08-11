package org.peie.avicultura.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class BackUp {
	
	 private String flagFile;
	    private File backUpFlagFile;
	    private File backUpRoot;
	    private final String backUpFolder;
	    private File toBackUpFileHandle;
	    private int maxBackupFiles = 10;
	    private static final String BACK_UP = "backUp";
	    private static final String BACKUPFILENAME = "back.up";
	    private Logger LOG = Logger.getLogger(BackUp.class);
	    private final FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File directory, String fileName) {
	            return fileName.equalsIgnoreCase(flagFile);
	        }
	    };

	    public BackUp() {
	        this.flagFile = BACKUPFILENAME;
	        this.backUpFolder = BACK_UP;
	    }

	    public BackUp(String flagFile, String backUpFolder) {
	        this.flagFile = flagFile;
	        this.backUpFolder = backUpFolder;
	    }

	    public void setMaxBackupFiles(int maxBackupFiles) {
	        this.maxBackupFiles = maxBackupFiles;
	    }
	    
	    

	    public boolean scan() throws IOException {
	        boolean check = false;
	        File[] roots = File.listRoots();
	        LOG.info("rl "+roots.length);
	        for (int i = 0; i < roots.length; i++) {
	        	LOG.info(roots[i]);

	            File res = listContent(roots[i]);
	            if (res != null) {
	                LOG.info("backup flag file >" + res.getAbsolutePath() + "< | root path >" + roots[i].getAbsolutePath() + "<");
	      
	                backUpFlagFile = res;
	                backUpRoot = roots[i];
	                check = true;
	                break;
	            }
	        }
	        
	        LOG.info(check);
	        
	        return check;
	    }

	    public void backup(String backupFilePath) throws IOException {
	        if (null != backUpFlagFile && null != backUpRoot) {

	            File backUpFolderHandle = new File(backUpRoot, backUpFolder);
	            toBackUpFileHandle = new File(backupFilePath);

	            DateFormat dfmt = new SimpleDateFormat("yyyyMMdd_HHmmss");

	            File dest = new File(backUpFolderHandle, toBackUpFileHandle.getName() + "." + dfmt.format(new Date()));

	            if (!backUpFolderHandle.exists()) {
	                backUpFolderHandle.mkdirs();
	            }

	            if (toBackUpFileHandle.exists()) {
	                try {
	                    FileUtils.copyFile(toBackUpFileHandle, dest, false);

	                } catch (IOException ex) {
	                    LOG.error(ex.getMessage(), ex);
	                    throw new IOException(ex.getMessage(), ex);
	                }
	            }

	        } else {
	            String errorMsg = "backup flag file >" + backUpFlagFile + "< or backup root >" + backUpRoot + "< are null";
	            LOG.error(errorMsg);
	            throw new IOException(errorMsg);
	        }
	    }

	    public void cleanup() throws IOException {
	        if (null != backUpRoot && null != toBackUpFileHandle) {
	            File backUpFolderHandle = new File(backUpRoot, backUpFolder);
	            List<Long> times = new ArrayList();
	            Map<Long, File> map = new HashMap();

	            FilenameFilter filterClean = new FilenameFilter() {
	                @Override
	                public boolean accept(File directory, String fileName) {
	                    return fileName.contains(toBackUpFileHandle.getName());
	                }
	            };

	            for (File f : backUpFolderHandle.listFiles(filterClean)) {

	                times.add(f.lastModified());
	                map.put(f.lastModified(), f);
	            }

	            Collections.sort(times);
	            int kill = times.size() - maxBackupFiles;

	            

	            for (int i = 0; i < kill; i++) {
	                LOG.info("delete: " + map.get(times.get(i)));
	                FileUtils.deleteQuietly(map.get(times.get(i)));
	            }

	        } else {
	            String errorMsg = "to backup flag file >" + toBackUpFileHandle + "< or backup root >" + backUpRoot + "< are null";
	            LOG.error(errorMsg);
	            throw new IOException(errorMsg);
	        }
	    }

	    private File listContent(File file) throws IOException {

	        File res = null;

	        if (file.isDirectory()) {
	            File[] tmp = file.listFiles(filter);
	            for (File f : tmp) {
	                
	                res = f;
	                break;
	            }
	        }
	        return res;

	    }

}
