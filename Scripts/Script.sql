select distinct kind.RINGNO,papa.RINGNO,mama.RINGNO,art.BIRDSPECIESNAME, kind.COLOR, kind.RINGAT, kind.GENDER, kind.SELLAT, kind.SELLADRESSE
from BIRDDATA kind,BIRDDATA papa, BIRDDATA mama, BIRDSPECIES art
where kind.RINGNO = '18489-13-003' 
and papa.RINGNO = TRIM(LEFT(kind.BIRDFATHER,POSITION(' ',kind.BIRDFATHER)))
and mama.RINGNO = TRIM(LEFT(kind.BIRDMOTHER,POSITION(' ',kind.BIRDMOTHER)))
and art.BIRDTYPEID = kind.BIRDTYPEID
and kind.MODFLAG = 0;


select distinct kind.BIRDFATHER,kind.BIRDMOTHER 
from BIRDDATA kind,BIRDDATA papa, BIRDDATA mama 
where LENGTH(kind.BIRDFATHER) > 3 
and LEngth(kind.BIRDMOTHER) > 3 
and kind.MODFLAG = 0
and papa.RINGNO = TRIM(LEFT(kind.BIRDFATHER,POSITION(' ',kind.BIRDFATHER)))
and mama.RINGNO = TRIM(LEFT(kind.BIRDMOTHER,POSITION(' ',kind.BIRDMOTHER)))