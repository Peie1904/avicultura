package org.peie.avicultura.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.peie.avicultura.main.Avicultura;
import org.peie.avicultura.viewer.AppNewBirdWindow;

public class DbHelper {
	private static final String MUTTER2 = "mutter";
	private static final String VATER2 = "vater";
	private static final String _0 = "0";
	public static final String NEUER_VOGEL = "Neue Vogelart";
	private Connection con;
	private static Logger log = Logger.getLogger(DbHelper.class);
	public static final String SQL_COMMENT_INDICATOR = "#";
	public static final String SQL_COMMENT_INDICATOR_2 = "--";
	public static final String SQL_COMMENT_INDICATOR_3 = "REM ";
	public static final String TABLE_BIRDPAIR = "BIRDPAIR_WITH_YEAR_TEST";

	private static final String importSql = "insert into birddata values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private PreparedStatement importStmt;
	private static final String importSpeciesSql = "insert into birdspecies values (?,?)";
	private PreparedStatement importSpeciesStmt;
	private static final String searchRingSql = "select RINGNO, BIRDSPECIESNAME , BREEDSTART , RINGAT ,BUYAT ,SELLAT ,DEATHAT,BIRDDATAID,BIRDPAIRID from birddata , birdspecies where birddata.birdtypeid =  birdspecies.birdtypeid and ringno like ? and MODFLAG = 0 order by RINGAT desc";
	private PreparedStatement searchRingStmt;
	private static final String searchBirdspeciesNameSql = "select BIRDSPECIESNAME,birdtypeid from birdspecies";
	private PreparedStatement searchBirdspeciesNameStmt;
	private static final String searchGenderSql = "select ringno,birdspeciesname  from birddata , birdspecies  where birddata.birdtypeid = birdspecies.birdtypeid and gender = ? and birdspecies.birdtypeid = ? and MODFLAG = 0 order by ringno desc";
	private PreparedStatement searchGenderStmt;
	private static final String checkRingNoSql = "select ringno from birddata where ringno = ? and MODFLAG = 0";
	private PreparedStatement checkRingNoStmt;
	private static final String selectAllSql = "select *,BIRDSPECIESNAME from birddata,BIRDSPECIES where ringno = ? and birddata.birdtypeid = birdspecies.birdtypeid and MODFLAG = 0 order by RINGAT";
	private PreparedStatement seletcAllStmt;
	private static final String hideBirdSql = "update birddata set MODFLAG = ? where BIRDDATAID = ?";
	private PreparedStatement hideBirdStmt;
	private static final String hideBirdByRingNoSql = "update birddata set MODFLAG = ? where ringno = ?";
	private PreparedStatement hideBirdByRingNoStmt;
	private static final String countDbSql = "select count(*) anzahl from birddata";
	private PreparedStatement countDbStmt;
	private static final String selectListSql = "select *,BIRDSPECIESNAME from birddata,BIRDSPECIES where birddata.birdtypeid = birdspecies.birdtypeid and MODFLAG = 0 order by RINGAT";
	private PreparedStatement seletctListStmt;
	private static final String selectStammBlattSql = "select distinct kind.RINGNO kindno,papa.RINGNO papano,mama.RINGNO mamano,art.BIRDSPECIESNAME,"
			+ " kind.COLOR, kind.RINGAT, kind.GENDER, kind.SELLAT, kind.SELLADRESSE "
			+ "from BIRDDATA kind,BIRDDATA papa, BIRDDATA mama, BIRDSPECIES art "
			+ "where kind.RINGNO = ? "
			+ "and papa.RINGNO = TRIM(LEFT(kind.BIRDFATHER,POSITION(' ',kind.BIRDFATHER))) "
			+ "and mama.RINGNO = TRIM(LEFT(kind.BIRDMOTHER,POSITION(' ',kind.BIRDMOTHER))) "
			+ "and art.BIRDTYPEID = kind.BIRDTYPEID and kind.MODFLAG = 0";
	private PreparedStatement selectStammBlattStmt;
	private static final String selectZuchtPaareSql = "select distinct papa.RINGNO paparing, "
			+ "TRIM(RIGHT(kind.BIRDFATHER,LENGTH(kind.BIRDFATHER)-POSITION(' ',kind.BIRDFATHER))) papavogel, "
			+ "papa.COLOR papafarbe, "
			+ "mama.RINGNO mamaring, "
			+ "TRIM(RIGHT(kind.BIRDMOTHER,LENGTH(kind.BIRDMOTHER)-POSITION(' ',kind.BIRDMOTHER))) mamavogel, "
			+ "mama.COLOR mamafarbe,kind.BIRDDATAID "
			+ "from BIRDDATA kind,BIRDDATA papa, BIRDDATA mama "
			+ "where LENGTH(kind.BIRDFATHER) > 3 "
			+ "and LEngth(kind.BIRDMOTHER) > 3 "
			+ "and kind.MODFLAG = 0 "
			+ "and papa.RINGNO = TRIM(LEFT(kind.BIRDFATHER,POSITION(' ',kind.BIRDFATHER))) "
			+ "and mama.RINGNO = TRIM(LEFT(kind.BIRDMOTHER,POSITION(' ',kind.BIRDMOTHER))) "
			// + "AND mama.MODFLAG = 0 and papa.MODFLAG = 0 "
			+ "order by papa.RINGNO,mama.RINGNO";
	private PreparedStatement selectZuchtPaareStmt;
	private static final String selectBirdPairSql = "select * from "
			+ TABLE_BIRDPAIR
			+ " where birdPairFATHER = ? and birdPairMOTHER = ?";
	private PreparedStatement selectBirdPairStmt;
	private static final String insertBirdPairSql = "insert into "
			+ TABLE_BIRDPAIR
			+ " (birdPairFATHER,birdPairMOTHER,birdPairYear) values (?,?,?)";
	private PreparedStatement insertBirdPairStmt;

	private static final String selectModflagForBirdSql = "select modflag from birddata where ringno = ? and COLOR = ?";
	private PreparedStatement selectModflagForBirdStmt;

	private static final String createTabeBirdPair = "create table `"
			+ TABLE_BIRDPAIR + "` " + "(`birdPairId` bigint auto_increment,"
			+ "`birdPairFATHER` VARCHAR(35) DEFAULT NULL,"
			+ "`birdPairMOTHER` VARCHAR(35) DEFAULT NULL,"
			+ "birdPairYear bigint DEFAULT 0," + "PRIMARY KEY (`birdPairId`))";

	private static final String selectPaarDataSql = "SELECT "
			+ "DISTINCT papa.RINGNO paparing , "
			+ "vogelpapa.BIRDSPECIESNAME papavogel , papa.COLOR papafarbe , "
			+ "mama.RINGNO mamaring , vogelmama.BIRDSPECIESNAME mamavogel , "
			+ "mama.COLOR mamafarbe , paar.BIRDPAIRID BIRDPAIRID,mama.modflag mamamod,papa.modflag papamod,paar.birdPairYear birdPairYear "
			+ "FROM "
			+ TABLE_BIRDPAIR
			+ " paar ,BIRDDATA mama , "
			+ "BIRDDATA papa ,BIRDSPECIES vogelmama ,"
			+ "BIRDSPECIES vogelpapa WHERE "
			+ "mama.RINGNO = paar.BIRDPAIRMOTHER "
			+ "AND papa.RINGNO = paar.BIRDPAIRFATHER "
			+ "and vogelmama.BIRDTYPEID = mama.BIRDTYPEID "
			+ "and vogelpapa.BIRDTYPEID = papa.BIRDTYPEID order by paar.birdPairId";

	private PreparedStatement selectPaarDataStmt;

	private static final String checkBirdPairSql = "select  BIRDTYPEID, GENDER from BIRDDATA where RINGNO = ? and MODFLAG = 0";
	private PreparedStatement checkBirdPairStmt;

	private static final String updateBirdPairSql = "update BIRDDATA set BIRDDATA.BIRDPAIRID = ? where BIRDDATA.BIRDFATHER like ? and BIRDDATA.BIRDMOTHER like ?";
	private PreparedStatement updateBirdPairStmt;

	private static final String getBirdPairYearSql = "select birdPairYear from "
			+ TABLE_BIRDPAIR + " where birdPairId = ?";
	private PreparedStatement getBirdPairYearStmt;
	private static final String getBirdPairAllSql = "select distinct "+ TABLE_BIRDPAIR +".*,BIRDDATA.BIRDTYPEID," +
			"BIRDSPECIES.BIRDSPECIESNAME " +
			"from "+ TABLE_BIRDPAIR +",BIRDDATA,BIRDSPECIES " +
			"where "+ TABLE_BIRDPAIR +".BIRDPAIRFATHER = BIRDDATA.RINGNO " +
			"and BIRDDATA.BIRDTYPEID = BIRDSPECIES.BIRDTYPEID " +
			"and BIRDSPECIES.BIRDSPECIESNAME like ? order by "+ TABLE_BIRDPAIR +".BIRDPAIRID desc";
	private PreparedStatement getBirdPairAllStmt;

	private String YEAR_NOW;

	public DbHelper(boolean newDb) throws AviculturaException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy");

		Date date = new Date();

		YEAR_NOW = df.format(date);

		con = createConnection();

		if (newDb) {
			createDbSchema();
		}

		try {
			searchRingStmt = con.prepareStatement(searchRingSql);
			searchBirdspeciesNameStmt = con
					.prepareStatement(searchBirdspeciesNameSql);

			importSpeciesStmt = con.prepareStatement(importSpeciesSql);
			searchGenderStmt = con.prepareStatement(searchGenderSql);
			checkRingNoStmt = con.prepareStatement(checkRingNoSql);
			seletcAllStmt = con.prepareStatement(selectAllSql);
			hideBirdStmt = con.prepareStatement(hideBirdSql);
			countDbStmt = con.prepareStatement(countDbSql);
			seletctListStmt = con.prepareStatement(selectListSql);
			hideBirdByRingNoStmt = con.prepareStatement(hideBirdByRingNoSql);
			selectStammBlattStmt = con.prepareStatement(selectStammBlattSql);
			selectZuchtPaareStmt = con.prepareStatement(selectZuchtPaareSql);
			selectModflagForBirdStmt = con
					.prepareStatement(selectModflagForBirdSql);
			createZuchtPaarTable();
			selectBirdPairStmt = con.prepareStatement(selectBirdPairSql);
			insertBirdPairStmt = con.prepareStatement(insertBirdPairSql);
			selectPaarDataStmt = con.prepareStatement(selectPaarDataSql);
			checkBirdPairStmt = con.prepareStatement(checkBirdPairSql);

			if (!createBirdPairColumn()) {
				updateBirdPairStmt = con.prepareStatement(updateBirdPairSql);
				fillPairTables();
			} else {
				updateBirdPairStmt = con.prepareStatement(updateBirdPairSql);
			}

			getBirdPairYearStmt = con.prepareStatement(getBirdPairYearSql);
			getBirdPairAllStmt = con.prepareStatement(getBirdPairAllSql);

			importStmt = con.prepareStatement(importSql);

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED,
					"preparation failed", e);
		}

	}

	private boolean createBirdPairColumn() throws AviculturaException {

		boolean check = existsColumn("BIRDDATA", "BIRDPAIRID");
		if (!check) {
			String sql = "ALTER TABLE BIRDDATA ADD BIRDPAIRID bigint";

			try {
				Statement stmt = con.createStatement();
				stmt.execute(sql);
			} catch (SQLException e) {
				log.error("get table error", e);
				throw new AviculturaException(
						AviculturaException.SQL_EXECUTION_FAILED,
						"get table error", e);
			}
		}

		return check;
	}

	private boolean existsColumn(String tableName, String columnName)
			throws AviculturaException {

		boolean check = false;

		ResultSet columns;
		try {
			columns = con.getMetaData().getColumns(con.getCatalog(), null,
					tableName, null);

			while (columns.next()) {
				String column = columns.getString(4);
				log.info(columns.getString(4));

				if (column.equals(columnName)) {
					log.info(columns.getString(4));
					check = true;
				}

			}

			columns.close();
		} catch (SQLException e) {
			log.error("get table error", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"get table error", e);
		}

		return check;

	}

	private boolean existsTable(String tableName) throws AviculturaException {
		boolean check = false;

		try {

			ResultSet tables = con.getMetaData().getTables(con.getCatalog(),
					null, null, null);

			while (tables.next()) {
				String table = tables.getString(3);

				log.info(tables.getString(3));

				if (table.equals(tableName)) {
					check = true;
				}

			}

			tables.close();

		} catch (SQLException e) {
			log.error("get table error", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"get table error", e);
		}

		return check;
	}

	private String getPairNo(ZuchtPaareObj zuchtPaareObj)
			throws AviculturaException {
		return getPairNo(zuchtPaareObj.getPaparing(),
				zuchtPaareObj.getMamaring());
	}

	private String getPairNo(String paparing, String mamaring)
			throws AviculturaException {
		String pairNo = _0;
		try {
			selectBirdPairStmt.setString(1, paparing);

			selectBirdPairStmt.setString(2, mamaring);

			ResultSet res = selectBirdPairStmt.executeQuery();

			while (res.next()) {
				pairNo = res.getInt("birdPairId") + "-"
						+ res.getInt("birdPairYear");
			}

			res.close();

		} catch (SQLException e) {
			log.info("pair there error", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"pair there error", e);
		}

		log.info(paparing + " + " + mamaring + " = " + pairNo);

		return pairNo;
	}

	public void createZuchtPaarTable() throws AviculturaException {

		boolean tableExists = existsTable(TABLE_BIRDPAIR);

		log.info(tableExists);

		if (!tableExists) {
			try {
				PreparedStatement createSql = con
						.prepareStatement(createTabeBirdPair);

				log.info(createTabeBirdPair);

				createSql.execute();

				createSql.close();

			} catch (SQLException e) {
				log.error("create BIRDPAIRS table error", e);
				throw new AviculturaException(
						AviculturaException.SQL_EXECUTION_FAILED,
						"create BIRDPAIRS table error", e);
			}
		}

	}

	public void fillPairTables() throws AviculturaException {
		List<ZuchtPaareObj> birdPairs = getZuchtPaareData();

		try {
			for (ZuchtPaareObj zuchtPaareObj : birdPairs) {
				boolean pairThere = false;

				if (!getPairNo(zuchtPaareObj).equals(_0)) {
					pairThere = true;
					log.info("pair there");
				}

				if (!pairThere) {
					insertBirdPairStmt
							.setString(1, zuchtPaareObj.getPaparing());
					insertBirdPairStmt
							.setString(2, zuchtPaareObj.getMamaring());
					insertBirdPairStmt.setString(3, YEAR_NOW);

					insertBirdPairStmt.execute();

					selectBirdPairStmt
							.setString(1, zuchtPaareObj.getPaparing());

					selectBirdPairStmt
							.setString(2, zuchtPaareObj.getMamaring());

					ResultSet res = selectBirdPairStmt.executeQuery();

					while (res.next()) {
						int id = res.getInt("BIRDPAIRID");
						log.info("BIRDPAIRID " + id);

						updateBirdPairStmt.setInt(1, id);
						updateBirdPairStmt.setString(2,
								zuchtPaareObj.getPaparing() + "%");

						updateBirdPairStmt.setString(3,
								zuchtPaareObj.getMamaring() + "%");

						updateBirdPairStmt.executeUpdate();
					}
					res.close();

				}

			}

		} catch (SQLException e) {
			log.info("pair there error", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"pair there error", e);
		}

	}

	public List<ZuchtPaareObj> getZuchtPaareDataForTable()
			throws AviculturaException {

		// createZuchtPaarTable();

		List<ZuchtPaareObj> zpoList = new ArrayList<ZuchtPaareObj>();

		ResultSet res;
		try {
			log.info(selectPaarDataSql);
			res = selectPaarDataStmt.executeQuery();

			while (res.next()) {
				ZuchtPaareObj zpo = new ZuchtPaareObj();
				String paparing = res.getString("paparing");
				String papavogel = res.getString("papavogel");
				String papafarbe = res.getString("papafarbe");
				String mamaring = res.getString("mamaring");
				String mamavogel = res.getString("mamavogel");
				String mamafarbe = res.getString("mamafarbe");
				String birdpairno = res.getString("BIRDPAIRID") + "-"
						+ res.getString("birdPairYear");

				long modm = res.getLong("mamamod");
				long modp = res.getLong("papamod");

				if (modm == 0 && modp == 0) {
					zpo.setPaparing(paparing);
					zpo.setPapavogel(papavogel);
					zpo.setPapafarbe(papafarbe);
					zpo.setMamaring(mamaring);
					zpo.setMamavogel(mamavogel);
					zpo.setMamafarbe(mamafarbe);
					zpo.setBirdpairno(birdpairno);
					// zpo.setBIRDDATAID(BIRDDATAID);

					zpoList.add(zpo);
				}
			}

			res.close();
		} catch (SQLException e) {
			log.error("database error: ", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"database error: " + e.getLocalizedMessage(), e);
		}

		return zpoList;

	}

	public List<ZuchtPaareObj> getZuchtPaareData() throws AviculturaException {

		// createZuchtPaarTable();

		List<ZuchtPaareObj> zpoList = new ArrayList<ZuchtPaareObj>();

		ResultSet res;
		try {
			log.info(selectZuchtPaareSql);
			res = selectZuchtPaareStmt.executeQuery();

			while (res.next()) {
				ZuchtPaareObj zpo = new ZuchtPaareObj();
				String paparing = res.getString("paparing");
				String papavogel = res.getString("papavogel");
				String papafarbe = res.getString("papafarbe");
				String mamaring = res.getString("mamaring");
				String mamavogel = res.getString("mamavogel");
				String mamafarbe = res.getString("mamafarbe");
				String BIRDDATAID = res.getString("BIRDDATAID");

				String birdpairno = getPairNo(paparing, mamaring);
				long modm = getModFlag(mamaring, mamafarbe);
				long modp = getModFlag(paparing, papafarbe);

				if (modm == 0 && modp == 0) {
					zpo.setPaparing(paparing);
					zpo.setPapavogel(papavogel);
					zpo.setPapafarbe(papafarbe);
					zpo.setMamaring(mamaring);
					zpo.setMamavogel(mamavogel);
					zpo.setMamafarbe(mamafarbe);
					zpo.setBirdpairno(birdpairno);
					zpo.setBIRDDATAID(BIRDDATAID);

					zpoList.add(zpo);
				}
			}

			res.close();
		} catch (SQLException e) {
			log.error("database error: ", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"database error: " + e.getLocalizedMessage(), e);
		}

		return zpoList;

	}

	public StammBlattObj getStammBlattData(String ringNo)
			throws AviculturaException {

		StammBlattObj sto = null;

		try {
			selectStammBlattStmt.setString(1, ringNo);

			ResultSet res = selectStammBlattStmt.executeQuery();

			while (res.next()) {
				log.info(res.getString("kindno"));
				sto = new StammBlattObj();

				String kindno = res.getString("kindno");
				String papano = res.getString("papano");
				String mamano = res.getString("mamano");
				String BIRDSPECIESNAME = res.getString("BIRDSPECIESNAME");
				String COLOR = res.getString("COLOR");
				long RINGAT = res.getLong("RINGAT");
				double GENDER = res.getDouble("GENDER");
				long SELLAT = res.getLong("SELLAT");
				String SELLADRESSE = res.getString("SELLADRESSE");

				sto.setKindno(kindno);
				sto.setPapano(papano);
				sto.setMamano(mamano);
				sto.setBIRDSPECIESNAME(BIRDSPECIESNAME);
				sto.setCOLOR(COLOR);
				sto.setSELLADRESSE(SELLADRESSE);
				sto.setRINGAT(RINGAT);
				sto.setSELLAT(SELLAT);
				sto.setGENDER(GENDER);

			}

			res.close();

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return sto;

	}

	public boolean checkImport() throws AviculturaException {

		boolean check = false;
		int anzahl = 1;

		try {
			ResultSet res = countDbStmt.executeQuery();

			while (res.next()) {
				anzahl = res.getInt("ANZAHL");
			}

			res.close();

			if (anzahl == 0) {
				check = true;
			}

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return check;
	}

	public List<GenderObj> getGender(String gender, String speciesName)
			throws AviculturaException {

		List<GenderObj> genderList = new ArrayList<GenderObj>();

		try {

			// System.out.println(speciesMapping.get(speciesName));

			searchGenderStmt.setString(1, gender);
			searchGenderStmt.setString(2, speciesMapping.get(speciesName));
			ResultSet res = searchGenderStmt.executeQuery();

			while (res.next()) {
				String ringNo = res.getString("ringno");
				String species = Helper.formatString(res
						.getString("birdspeciesname"));

				GenderObj obj = new GenderObj(ringNo, species);

				genderList.add(obj);

			}

			res.close();
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"can not execute", e);
		}

		return genderList;
	}

	public Map<String, String> getSpeciesMapping() {
		return speciesMapping;
	}

	private Connection createConnection() throws AviculturaException {
		Connection con = null;
		String driver = "org.h2.Driver";
		String dataBase = "jdbc:h2:" + Avicultura.DATABASE;

		log.info(dataBase);

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(dataBase, "sa", "");
		} catch (ClassNotFoundException ex) {
			throw new AviculturaException(AviculturaException.CLASS_NOTFOUND,
					"can not found driver " + driver, ex);
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "can not connect "
							+ dataBase, e);
		}

		return con;
	}

	private Map<String, String> speciesMapping = new HashMap<String, String>();

	public List<SpeciesObj> getBirdSpecies() throws AviculturaException {

		List<SpeciesObj> names = new ArrayList<SpeciesObj>();

		try {
			ResultSet res = searchBirdspeciesNameStmt.executeQuery();

			while (res.next()) {
				String name = Helper.formatString(res
						.getString("BIRDSPECIESNAME"));
				String id = res.getString("birdtypeid");

				speciesMapping.put(name, id);
				names.add(new SpeciesObj(name, id));
			}

			res.close();

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"can not execute", e);
		}

		names.add(new SpeciesObj(NEUER_VOGEL, ""));

		return names;
	}

	public void createDbSchema() {
		executeSqlFromFile(con, "createDataBase.sql", false);
	}

	public boolean isRingNoExists(String ringNo) throws AviculturaException {
		boolean exists = false;
		try {
			checkRingNoStmt.setString(1, ringNo);

			ResultSet res = checkRingNoStmt.executeQuery();

			while (res.next()) {
				exists = true;
				log.info("ringno.: " + ringNo + " exists ");
			}

			res.close();

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, "", e);
		}

		return exists;
	}

	public void closeConnection() throws AviculturaException {
		try {

			searchRingStmt.close();
			searchBirdspeciesNameStmt.close();
			importStmt.close();
			importSpeciesStmt.close();
			searchGenderStmt.close();
			checkRingNoStmt.close();
			seletcAllStmt.close();
			hideBirdStmt.close();
			seletctListStmt.close();
			hideBirdByRingNoStmt.close();
			selectStammBlattStmt.close();
			selectZuchtPaareStmt.close();
			selectBirdPairStmt.close();
			insertBirdPairStmt.close();
			selectPaarDataStmt.close();
			selectModflagForBirdStmt.close();
			checkBirdPairStmt.close();
			updateBirdPairStmt.close();
			getBirdPairYearStmt.close();
			getBirdPairAllStmt.close();
			con.close();

			log.info("close database");
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED,
					"can not close connect ", e);
		} catch (NullPointerException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED,
					"can not close connect a empty db", e);
		}
	}
	
	public List<ZuchtPaarAuswahlObj> getBirdPairAll(String birdType) throws AviculturaException{
		
		List<ZuchtPaarAuswahlObj> list = new ArrayList<ZuchtPaarAuswahlObj>();
		
		try {
			getBirdPairAllStmt.setString(1, birdType);
			ResultSet res = getBirdPairAllStmt.executeQuery();
			
			while (res.next()){
				log.info(res.getInt("BIRDPAIRID"));
				
				ZuchtPaarAuswahlObj zpao = new ZuchtPaarAuswahlObj();
				
				zpao.setPaarId(res.getInt("BIRDPAIRID")+"-"+res.getInt("BIRDPAIRYEAR"));
				zpao.setFather(res.getString("BIRDPAIRFATHER"));
				zpao.setMother(res.getString("BIRDPAIRMOTHER"));
				
				list.add(zpao);
			}
			
			res.close();
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"database error: " + e.getLocalizedMessage(), e);
		}
		
		return list;
		
	}

	public String getBirdPairNo(int birdPairId) throws AviculturaException {
		String out = "";

		if (birdPairId != 0) {

			try {
				getBirdPairYearStmt.setInt(1, birdPairId);

				ResultSet res = getBirdPairYearStmt.executeQuery();

				while (res.next()) {
					out = birdPairId + "-" + res.getInt("birdPairYear");
				}

				res.close();
			} catch (SQLException e) {
				throw new AviculturaException(
						AviculturaException.SQL_EXECUTION_FAILED,
						"database error: " + e.getLocalizedMessage(), e);
			}
		}

		return out;
	}

	public long getModFlag(String ringNo, String color)
			throws AviculturaException {
		long out = 1;

		try {

			selectModflagForBirdStmt.setString(1, ringNo);
			selectModflagForBirdStmt.setString(2, color);

			ResultSet res = selectModflagForBirdStmt.executeQuery();

			while (res.next()) {
				out = res.getLong("MODFLAG");
			}

		} catch (SQLException e) {
			log.error("database error: ", e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"database error: " + e.getLocalizedMessage(), e);
		}

		return out;
	}

	public void importVisualBasicVersion(String path)
			throws AviculturaException {

		try {
			importStmt = con.prepareStatement(importSql);
			importSpeciesStmt = con.prepareStatement(importSpeciesSql);
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		File importFileData = new File(path + File.separator + "data.bbb");
		File importFileSpecies = new File(path + File.separator
				+ "birdspecies.bbb");

		log.info(Helper.getPid());

		Map<String, String> species = null;
		if (importFileSpecies.exists()) {
			species = importSpeciesTable(importFileSpecies);
		}

		if (importFileData.exists()) {
			readImport(importFileData, species);
		}

	}

	private Map<String, String> importSpeciesTable(File file)
			throws AviculturaException {
		Map<String, String> species = new HashMap<String, String>();

		try {
			// BufferedReader in = new BufferedReader(new FileReader(file));
			BufferedReader inReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line;
			String[] tmp;

			while ((line = inReader.readLine()) != null) {
				tmp = line.split("<=>");

				if (tmp.length == 2) {
					try {
						String uid = Helper.getPid();

						importSpeciesStmt.setString(1, uid);
						importSpeciesStmt.setString(2,
								Helper.formatString(tmp[0]));
						importSpeciesStmt.execute();

						species.put(tmp[0], uid);
					} catch (SQLException e) {
						throw new AviculturaException(
								AviculturaException.DB_CONNECT_FAILED,
								"database error: " + e.getLocalizedMessage(), e);
					}
				}
			}

			inReader.close();
		} catch (IOException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return species;
	}

	public void dbIn(String sql) throws AviculturaException {

		try {
			Statement stmt = con.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, "", e);
		}
	}

	private double stringToDoubel(String in) {
		return Double.parseDouble(in);
	}

	private long getTimeStamp(String datum) {

		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		long result = 0;

		if (datum.length() != 0) {
			log.info(datum);
			try {
				Date date = df.parse(datum);
				log.info(date);
				result = date.getTime();
			} catch (ParseException e) {
				result = 0;
				log.error(datum + " " + result, e);
			}

		}
		return result;
	}

	public static final String DB_FIELDS = "BIRDDATAID, BIRDTYPEID, RINGNO, RINGTYPE, BREEDSTART, RINGAT, BUYAT, BUYADRESSE, SELLAT,"
			+ " SELLADRESSE, DEATHAT, DEATHNOTE, MEDICSTART, MEDICNOTE, MEDICEND, MEDICCONTROL, DOC, OMBUDSMAN, OFFICE, GENDER, COLOR, BIRDFATHER, BIRDMOTHER";

	public List<BirdObject> getBirdList() throws AviculturaException {

		List<BirdObject> list = new ArrayList<BirdObject>();

		try {

			ResultSet res = seletctListStmt.executeQuery();

			while (res.next()) {
				BirdObject bird = new BirdObject();
				bird.setRingNo(res.getString("RINGNO"));
				bird.setSpeciesID(Helper.formatString(res
						.getString("BIRDSPECIESNAME")));
				bird.setRingType(res.getBoolean("RINGTYPE"));
				bird.setColor(res.getString("COLOR"));
				if (res.getString("GENDER").equals(AppNewBirdWindow._1_0)) {
					bird.maleIsTrue();
				} else {
					bird.femaleIsTrue();
				}

				bird.setComboBoxMale(res.getString("BIRDFATHER"));
				bird.setComboBoxFemale(res.getString("BIRDMOTHER"));
				bird.setBreedStart(Helper.getDateString(res
						.getLong("BREEDSTART")));
				bird.setRingAt(Helper.getDateString(res.getLong("RINGAT")));
				bird.setBuyAt(Helper.getDateString(res.getLong("BUYAT")));
				bird.setBuyFrom(res.getString("BUYADRESSE"));
				bird.setSellAt(Helper.getDateString(res.getLong("SELLAT")));
				bird.setSellTo(res.getString("SELLADRESSE"));
				bird.setDeathAt(Helper.getDateString(res.getLong("DEATHAT")));
				bird.setDeathWhy(res.getString("DEATHNOTE"));
				bird.setMedicStart(Helper.getDateString(res
						.getLong("MEDICSTART")));
				bird.setMedicEnd(Helper.getDateString(res.getLong("MEDICEND")));
				bird.setMedicComment(res.getString("MEDICNOTE"));
				bird.setMedicCheck(res.getString("MEDICCONTROL"));
				bird.setDocu(res.getString("DOC"));
				bird.setObtman(res.getString("OMBUDSMAN"));
				bird.setGov(res.getString("OFFICE"));
				bird.setBirdDataId(res.getString("BIRDDATAID"));

				list.add(bird);

			}

			res.close();
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return list;
	}

	public BirdObject getBirdEdit(String ringNo) throws AviculturaException {

		BirdObject bird = new BirdObject();

		try {
			seletcAllStmt.setString(1, ringNo);

			ResultSet res = seletcAllStmt.executeQuery();

			while (res.next()) {
				bird.setRingNo(res.getString("RINGNO"));
				bird.setSpeciesID(Helper.formatString(res
						.getString("BIRDSPECIESNAME")));
				bird.setRingType(res.getBoolean("RINGTYPE"));
				bird.setColor(res.getString("COLOR"));
				if (res.getString("GENDER").equals(AppNewBirdWindow._1_0)) {
					bird.maleIsTrue();
				} else {
					bird.femaleIsTrue();
				}

				bird.setComboBoxMale(res.getString("BIRDFATHER"));
				bird.setComboBoxFemale(res.getString("BIRDMOTHER"));
				bird.setBreedStart(Helper.getDateString(res
						.getLong("BREEDSTART")));
				bird.setRingAt(Helper.getDateString(res.getLong("RINGAT")));
				bird.setBuyAt(Helper.getDateString(res.getLong("BUYAT")));
				bird.setBuyFrom(res.getString("BUYADRESSE"));
				bird.setSellAt(Helper.getDateString(res.getLong("SELLAT")));
				bird.setSellTo(res.getString("SELLADRESSE"));
				bird.setDeathAt(Helper.getDateString(res.getLong("DEATHAT")));
				bird.setDeathWhy(res.getString("DEATHNOTE"));
				bird.setMedicStart(Helper.getDateString(res
						.getLong("MEDICSTART")));
				bird.setMedicEnd(Helper.getDateString(res.getLong("MEDICEND")));
				bird.setMedicComment(res.getString("MEDICNOTE"));
				bird.setMedicCheck(res.getString("MEDICCONTROL"));
				bird.setDocu(res.getString("DOC"));
				bird.setObtman(res.getString("OMBUDSMAN"));
				bird.setGov(res.getString("OFFICE"));
				bird.setBirdDataId(res.getString("BIRDDATAID"));
				bird.setBirdPairId(res.getInt("BirdPairId"));

			}

			res.close();
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return bird;
	}

	public String newSpecies(String speciesName) throws AviculturaException {
		String pid = Helper.getPid();
		try {
			importSpeciesStmt.setString(1, pid);
			importSpeciesStmt.setString(2, speciesName);

			importSpeciesStmt.execute();
		} catch (SQLException e) {
			log.error(speciesName + " " + pid, e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, speciesName + " "
							+ pid, e);
		}

		return pid;
	}

	public boolean birdHide(String ringNo) throws AviculturaException {
		boolean check = false;
		try {
			hideBirdByRingNoStmt.setLong(1, System.currentTimeMillis());
			hideBirdByRingNoStmt.setString(2, ringNo);

			if (hideBirdByRingNoStmt.executeUpdate() != 0) {
				check = true;
			}

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}
		return check;

	}

	public boolean birdModDb(BirdObject bird, String birdDataId)
			throws AviculturaException {
		log.info(birdDataId);
		boolean check = false;

		try {
			hideBirdStmt.setLong(1, System.currentTimeMillis());
			hideBirdStmt.setString(2, birdDataId);

			if (hideBirdStmt.executeUpdate() != 0) {
				birdIntoDb(bird);
				check = true;
			}

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, bird.getRingNo()
							+ " " + birdDataId, e);
		}
		return check;
	}

	public void birdIntoDb(BirdObject bird) throws AviculturaException {

		String pid = Helper.getPid();

		long modFlag = 0L;

		try {
			importStmt.setString(1, pid);
			importStmt.setString(2, bird.getSpeciesID());
			importStmt.setString(3, bird.getRingNo());
			if (bird.isRingType()) {
				importStmt.setString(4, "True");
			} else {
				importStmt.setString(4, "False");
			}

			importStmt.setLong(5, Helper.dateToTimeStamp(bird.getBreedStart()));
			importStmt.setLong(6, Helper.dateToTimeStamp(bird.getRingAt()));
			importStmt.setLong(7, Helper.dateToTimeStamp(bird.getBuyAt()));
			importStmt.setString(8, bird.getBuyFrom());
			importStmt.setLong(9, Helper.dateToTimeStamp(bird.getSellAt()));
			importStmt.setString(10, bird.getSellTo());
			importStmt.setLong(11, Helper.dateToTimeStamp(bird.getDeathAt()));
			importStmt.setString(12, bird.getDeathWhy());
			importStmt
					.setLong(13, Helper.dateToTimeStamp(bird.getMedicStart()));
			importStmt.setString(14, bird.getMedicComment());
			importStmt.setLong(15, Helper.dateToTimeStamp(bird.getMedicEnd()));
			importStmt.setString(16, bird.getMedicCheck());
			importStmt.setString(17, bird.getDocu());
			importStmt.setString(18, bird.getObtman());
			importStmt.setString(19, bird.getGov());
			if (bird.isMale()) {
				importStmt.setDouble(20, 1.0);
			} else {
				importStmt.setDouble(20, 0.1);
			}
			importStmt.setString(21, bird.getColor());
			importStmt.setString(22, bird.getComboBoxMale());
			importStmt.setString(23, bird.getComboBoxFemale());
			importStmt.setLong(24, modFlag);
			importStmt.setInt(25, bird.getBirdPairId());

			importStmt.execute();
		} catch (SQLException e) {

			log.error(bird.getRingNo() + " " + pid, e);
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, bird.getRingNo()
							+ " " + pid, e);
		}
	}

	private void readImport(File file, Map<String, String> species)
			throws AviculturaException {

		try {
			// BufferedReader in = new BufferedReader(new FileReader(file));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), "UTF-8"));
			String line = null;
			String[] tmp, iTmp;

			while ((line = in.readLine()) != null) {
				// log.info(line);
				tmp = line.split("<=>");
				// String sql =
				// "insert into "+table+" values ('"+Helper.getPid()+"',";
				if (tmp.length == 2) {
					iTmp = tmp[1].split(";");

					try {
						importStmt.setString(1, Helper.getPid());
						importStmt.setString(2, species.get(iTmp[0]));
						importStmt.setString(3,
								Helper.formatStringImport(iTmp[1]));
						importStmt.setString(4,
								Helper.formatStringImport(iTmp[2]));
						importStmt.setLong(5, getTimeStamp(iTmp[3]));
						importStmt.setLong(6, getTimeStamp(iTmp[4]));
						importStmt.setLong(7, getTimeStamp(iTmp[5]));
						importStmt.setString(8,
								Helper.formatStringImport(iTmp[6]));
						importStmt.setLong(9, getTimeStamp(iTmp[7]));
						importStmt.setString(10,
								Helper.formatStringImport(iTmp[8]));
						importStmt.setLong(11, getTimeStamp(iTmp[9]));
						importStmt.setString(12,
								Helper.formatStringImport(iTmp[10]));
						importStmt.setLong(13, getTimeStamp(iTmp[11]));
						importStmt.setString(14,
								Helper.formatStringImport(iTmp[12]));
						importStmt.setLong(15, getTimeStamp(iTmp[13]));
						importStmt.setString(16,
								Helper.formatStringImport(iTmp[14]));
						importStmt.setString(17,
								Helper.formatStringImport(iTmp[15]));
						importStmt.setString(18,
								Helper.formatStringImport(iTmp[16]));
						importStmt.setString(19,
								Helper.formatStringImport(iTmp[17]));
						importStmt.setDouble(20, stringToDoubel(iTmp[19]));
						importStmt.setString(21,
								Helper.formatStringImport(iTmp[18]));
						importStmt.setString(22, "");
						importStmt.setString(23, "");
						importStmt.setLong(24, 0);

						importStmt.execute();
					} catch (SQLException e) {

						log.error(iTmp[0] + " " + species.get(iTmp[0]), e);
					}

				}

			}

			in.close();
		} catch (IOException e) {

			throw new AviculturaException(AviculturaException.IO_ERROR,
					"can not read import file: " + file.getAbsolutePath(), e);
		}

	}

	public Map<Long, SearchResults> searchFor(String searchString,
			String searchType) throws AviculturaException {
		// List<SearchResults> result = new ArrayList<SearchResults>();
		Map<Long, SearchResults> result = new TreeMap<Long, SearchResults>();
		ResultSet res = null;

		try {
			searchRingStmt.setString(1, searchString);
			res = searchRingStmt.executeQuery();

			while (res.next()) {
				SearchResults search = new SearchResults(
						res.getString("RINGNO"),
						res.getString("BIRDSPECIESNAME"),
						res.getLong("BREEDSTART"), res.getLong("RINGAT"),
						res.getLong("BUYAT"), res.getLong("SELLAT"),
						res.getLong("DEATHAT"), res.getString("BIRDDATAID"),
						res.getInt("BIRDPAIRID"));

				if (res.getLong("BREEDSTART") > 0) {
					long key = res.getLong("RINGAT") * -1L;

					while (true) {
						if (result.containsKey(key)) {
							key = key - 1;
						} else {
							break;
						}
					}

					result.put(key, search);

				} else {
					long key = res.getLong("BUYAT") * -1L;

					while (true) {
						if (result.containsKey(key)) {
							key = key - 1;
						} else {
							break;
						}
					}

					result.put(key, search);
				}

			}
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.DB_CONNECT_FAILED, "database error: "
							+ e.getLocalizedMessage(), e);
		}

		return result;
	}

	private void executeSqlFromFile(Connection con, String fileName,
			boolean continueOnErrors) {
		BufferedReader reader = null;

		InputStream is = ClassLoader.getSystemResourceAsStream(fileName);

		log.info("executeSqlFromFile " + fileName);

		/*
		 * if ( con == null ) { throw new IllegalArgumentException(
		 * "need non null DB connection" ); }
		 * 
		 * if ( file == null ) { throw new IllegalArgumentException(
		 * "need non null input file" ); } else if ( !file.isFile() ) { throw
		 * new IllegalArgumentException( "given path is not a plain file '" +
		 * file.getAbsolutePath() + "'" ); } else if ( !file.canRead() ) { throw
		 * new IllegalArgumentException( "given file is read protected '" +
		 * file.getAbsolutePath() + "'" ); }
		 */

		InputStreamReader isr = new InputStreamReader(is);

		reader = new BufferedReader(isr);

		StringBuffer buff = new StringBuffer();
		String line = null;

		try {

			while ((line = reader.readLine()) != null) {
				line = line.trim();

				if (line.length() == 0) {
					continue;
				}

				// Filter comment lines:
				if (line.startsWith(SQL_COMMENT_INDICATOR)) {
					// do nothing
				} else if (line.startsWith(SQL_COMMENT_INDICATOR_2)) {
					// do nothing
				} else if (line.startsWith(SQL_COMMENT_INDICATOR_3)) {
					// do nothing
				} else {
					buff.append(" " + line);
				}
			}
		} catch (IOException ex) {
			log.error("Error while reading sql script from file ", ex);
			throw new IllegalStateException("Failed to read sql from file >"
					+ fileName + "<");
		} finally {

			try {
				reader.close();
			} catch (IOException ioexp) {
				log.error("failed to close stream", ioexp);
				throw new IllegalStateException(
						"failed to close stream from resource");
			}
		}

		String sqlScript = buff.toString();

		if (sqlScript.trim().length() == 0) {
			throw new IllegalStateException("got empty SQL script from "
					+ fileName);
		}

		String[] sqlCmds = sqlScript.split(";");
		Statement sta = null;

		// try {
		//
		// con.setAutoCommit( true );
		// } catch ( SQLException ex ) {
		// log.error( "Could not set Auto-Commit to true. ", ex );
		// throw new IllegalStateException(
		// "Could not set Auto-Commit to true. " );
		// }

		long sumTime = 0;

		for (int i = 0; i < sqlCmds.length; i++) {

			long startTime = System.currentTimeMillis();

			try {

				log.debug("Executing statement '" + sqlCmds[i] + "'");

				sta = con.createStatement();

				// Create the db schema as necessary:
				sta.execute(sqlCmds[i]);

				// Cleanup
				sta.close();
				sta = null;
			} catch (SQLException e) {
				log.error(
						"Error while executing sql script from file: statement no. "
								+ (i + 1) + " >" + sqlCmds[i] + "<", e);

				if (!continueOnErrors) {
					throw new IllegalStateException(
							"Failed to read or execute sql from >" + fileName
									+ "<");
				}
			} catch (NullPointerException exp) {
				log.error("Failed to read or execute sql from >" + fileName
						+ "<", exp);
				throw new IllegalStateException(
						"Failed to read or execute sql from >" + fileName + "<");
			} finally {

				if (sta != null) {

					try {
						sta.close();
					} catch (SQLException exp) {
						log.warn(
								"an error occured closing statement in error handling",
								exp);
					}
				}
			}

			long endTime = System.currentTimeMillis();
			long opTime = (endTime - startTime) / 1000;

			log.debug(">>> Statement took " + opTime + " sec.\n\n");
			sumTime += (endTime - startTime);
		}

		sqlCmds = null;
		log.info(">>>> Loading " + fileName + " took " + (sumTime / 1000.0)
				+ " sec.\n\n");
	}

	public final static int CHECK_VATER_NOT = -1;
	public final static int CHECK_MUTTER_NOT = -2;
	public final static int CHECK_TYPE_NOT_EQUAL = -3;
	public final static int CHECK_GENDER_NOT = -4;
	public final static int CHECK_GENDER_NOT_10 = -5;
	public final static int CHECK_GENDER_NOT_01 = -6;
	public final static int CHECK_PAIR_EXSISTS = -7;

	public int checkBirdPair(String vater, String mutter)
			throws AviculturaException {
		String vaterBirdId = VATER2;
		double vaterGender = 0.0;
		String mutterBirdId = MUTTER2;
		double mutterGender = 0.0;

		log.info(vater + " " + mutter);

		try {
			checkBirdPairStmt.setString(1, vater);
			ResultSet res = checkBirdPairStmt.executeQuery();

			while (res.next()) {
				vaterBirdId = res.getString("BIRDTYPEID");
				vaterGender = res.getDouble("GENDER");
			}

			res.close();

			checkBirdPairStmt.setString(1, mutter);
			res = checkBirdPairStmt.executeQuery();

			while (res.next()) {
				mutterBirdId = res.getString("BIRDTYPEID");
				mutterGender = res.getDouble("GENDER");
			}

			res.close();

		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, e.getMessage(), e);
		}

		log.info(vaterBirdId + " " + mutterBirdId);

		if (vaterBirdId.equals(VATER2)) {
			return CHECK_VATER_NOT;
		}

		if (mutterBirdId.equals(MUTTER2)) {
			return CHECK_MUTTER_NOT;
		}

		if (!vaterBirdId.equals(mutterBirdId)) {
			return CHECK_TYPE_NOT_EQUAL;
		}

		double summary = vaterGender + mutterGender;

		log.info(vaterGender + " + " + mutterGender + " = " + summary);

		if (summary != 1.1) {
			return CHECK_GENDER_NOT;
		}

		if (vaterGender != 1.0) {
			return CHECK_GENDER_NOT_10;
		}

		if (mutterGender != 0.1) {
			return CHECK_GENDER_NOT_01;
		}

		boolean exsists = false;

		try {
			selectBirdPairStmt.setString(1, vater);
			selectBirdPairStmt.setString(2, mutter);

			ResultSet res = selectBirdPairStmt.executeQuery();

			while (res.next()) {
				exsists = true;
			}

			res.close();

		} catch (SQLException e1) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED, e1.getMessage(),
					e1);
		}

		if (exsists) {
			return CHECK_PAIR_EXSISTS;
		}

		try {
			insertBirdPairStmt.setString(1, vater);
			insertBirdPairStmt.setString(2, mutter);
			insertBirdPairStmt.setString(3, YEAR_NOW);
			insertBirdPairStmt.execute();
		} catch (SQLException e) {
			throw new AviculturaException(
					AviculturaException.SQL_EXECUTION_FAILED,
					"kann Paar nicht eintragen", e);
		}

		return 0;

	}

}
