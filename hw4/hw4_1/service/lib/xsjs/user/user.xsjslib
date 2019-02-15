/**
 @param {connection} Connection - The SQL connection used in the OData request
 @param {beforeTableName} String - The name of a temporary table with the single entry before the operation (UPDATE and DELETE events only)
 @param {afterTableName} String -The name of a temporary table with the single entry after the operation (CREATE and UPDATE events only)
 */
const servicelb = $.import('xsjs.user', 'CService').CService;
const service = new servicelb($.hdb.getConnection({
    treatDateAsUTC: true
}));
var user = function (connection) {

    const USER_TABLE = "hw3::User";
    /*
            const USER = $.session.securityContext.userInfo.familyName ?
                $.session.securityContext.userInfo.familyName + " " + $.session.securityContext.userInfo.givenName :
                $.session.getUsername().toLocaleLowerCase(),
    */


   this.doPost = function (oUser) {
        oUser.usid = service.getNextval("hw3::usid");
        const statement = service.createPreparedInsertStatement(USER_TABLE, oUser);
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.CREATED;
        $.response.setBody(JSON.stringify(oUser));
    };



    this.doPut = function (obj) {
        const statement = service.createPreparedUpdateStatement(USER_TABLE, obj);
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.CREATED;
        $.response.setBody(JSON.stringify(obj));
    };

    this.doGet = function(){
        const statement = 'select * from "hw3::User"';
        const result = connection.executeQuery(statement);
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify(result));
    }

    this.doDelete = function (usid) {
        const statement = service.createPreparedDeleteStatement(USER_TABLE, {usid: usid});
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify({}));
    };
};
