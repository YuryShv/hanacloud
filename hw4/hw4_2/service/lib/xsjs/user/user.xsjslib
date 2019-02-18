var user = function (connection) {
const servicelb = $.import('xsjs.user', 'CService').CService;
const service = new servicelb(connection);
    const USER_TABLE = "hw3::User";
    const USER_ID = '"hw3::usid"';

   this.doPost = function (oUser) {
        oUser.usid = service.getNextval(USER_ID);
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

    this.doGet = function () {
        const result = connection.executeQuery('SELECT * FROM "' + USER_TABLE + '"');
        result.forEach(x => traceErr(JSON.stringify(x)));
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify(result));
    };

    this.doDelete = function (usid) {
        const statement = service.createPreparedDeleteStatement(USER_TABLE, {usid: usid});
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify({}));
    };
};
