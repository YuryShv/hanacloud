/**
 @param {connection} Connection - The SQL connection used in the OData request
 @param {beforeTableName} String - The name of a temporary table with the single entry before the operation (UPDATE and DELETE events only)
 @param {afterTableName} String -The name of a temporary table with the single entry after the operation (CREATE and UPDATE events only)
 */
    const servicelb = $.import('xsjs.user', 'CService').CService;
    const service = new servicelb($.hdb.getConnection({
    treatDateAsUTC: true
}));
    const USER_TABLE = "hw3::User";
    const SEQ_NAME =   "hw3::usid";

function usersCreate(param){
    $.trace.error(JSON.stringify(param));
    var after = param.afterTableName;
    var	pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
     $.trace.error("Oresult: "+oResult);
    var oUserItems = service.recordSetToJSON(oResult, "items");
    var oUser = oUserItems.items[0];
    $.trace.error(JSON.stringify(oUser));
    pStmt = param.connection.prepareStatement('select "hw3::usid".NEXTVAL from dummy'); 
	var result = pStmt.executeQuery();
    $.trace.error("result: "+result);
    while (result.next()) {
		oUser.usid = result.getString(1);
	}
    $.trace.error(JSON.stringify(oUser));
	pStmt.close();
		var pStmt;
		pStmt = param.connection.prepareStatement(`insert into "${USER_TABLE}" values(?,?) `);
        service.fillData(pStmt,oUser);			
		pStmt = param.connection.prepareStatement(`TRUNCATE TABLE "${after}"`);
		pStmt = param.connection.prepareStatement(`insert into "${after}" values(?,?)`);		
        service.fillData(pStmt,oUser);	
}
function usersUpdate(param){
    var after = param.afterTableName;
    var pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
    var oUserItems = service.recordSetToJSON(oResult, "items");
    var oUser = oUserItems.items[0];
    $.trace.error(JSON.stringify(oUser));
    var uStmt;
    uStmt = param.connection.prepareStatement(`UPDATE "${USER_TABLE}" SET "name"='${oUser.name}' WHERE "usid"=${oUser.usid};`);
    uStmt.executeQuery();
}

function usersDelete(param){
    var after = param.afterTableName;
    var pStmt = param.connection.prepareStatement(`select * from "${after}"`);
    var oResult = pStmt.executeQuery();
    var oUserItems = service.recordSetToJSON(oResult, "items");
    var oUser = oUserItems.items[0];
    $.trace.error(JSON.stringify(oUser));
    var uStmt;
    uStmt = param.connection.prepareStatement(`delete from "${USER_TABLE}" WHERE "usid"=${oUser.usid};`);
    uStmt.executeQuery();    
}

