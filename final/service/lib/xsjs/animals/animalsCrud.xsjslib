    const servicelb = $.import('xsjs.animals', 'CService').CService;
    const service = new servicelb($.hdb.getConnection({
    treatDateAsUTC: true
}));
    const CURR_TIMESTAMP_FUN = "current_timestamp";
    const ANIMAL_TABLE = "hw3::Animals";
    const SEQ_NAME =   "hw3::aid";

function animalsCreate(param){
    var after = param.afterTableName;
    var	pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
    var oAnimalItems = service.recordSetToJSON(oResult, "items");
    var oAnimal = oAnimalItems.items[0];
    pStmt = param.connection.prepareStatement(`select "${SEQ_NAME}".NEXTVAL from dummy`); 
	var result = pStmt.executeQuery();
    while (result.next()) {
		oAnimal.aid = result.getString(1);
	}
	pStmt.close();
	pStmt = param.connection.prepareStatement(`insert into "${ANIMAL_TABLE}" values(?,?,?,?,?) `);
    service.fillData(pStmt,oAnimal);			
	pStmt = param.connection.prepareStatement(`TRUNCATE TABLE "${after}"`);
	pStmt = param.connection.prepareStatement(`insert into "${after}" values(?,?,?,?,?)`);		
    service.fillData(pStmt,oAnimal);	
}
function animalsUpdate(param){
    var after = param.afterTableName;
    var pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
    var oAnimalItems = service.recordSetToJSON(oResult, "items");
    var oAnimal = oAnimalItems.items[0];
    var uStmt;
    uStmt = param.connection.prepareStatement(`UPDATE "${ANIMAL_TABLE}" SET "aname"='${oAnimal.aname}', "akind" = '${oAnimal.akind}',"ts_update" = ${CURR_TIMESTAMP_FUN} WHERE "aid"=${oAnimal.aid};`);
    uStmt.executeQuery();
}

function animalsDelete(param){
    var after = param.afterTableName;
    var pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
    var oAnimalItems = service.recordSetToJSON(oResult, "items");
    var oAnimal = oAnimalItems.items[0];
    var uStmt;
    uStmt = param.connection.prepareStatement(`delete from "${ANIMAL_TABLE}" WHERE "aid"=${oAnimal.aid};`);
    $.trace.error(uStmt);
    uStmt.executeQuery();    
}

