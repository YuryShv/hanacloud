var animals = function (connection) {
const servicelb = $.import('xsjs.animals', 'CService').CService;
const service = new servicelb(connection);
    const ANIMAL_TABLE = "hw3::Animals";
    const ANIMAL_ID = '"hw3::aid"';

   this.doPost = function (oAnimal) {
        oAnimal.aid = service.getNextval(ANIMAL_ID);
        const statement = service.createPreparedInsertStatement(ANIMAL_TABLE, oAnimal);
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.CREATED;
        $.response.setBody(JSON.stringify(oAnimal));
    };



    this.doPut = function (obj) {
        const statement = service.createPreparedUpdateStatement(ANIMAL_TABLE, obj);
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.CREATED;
        $.response.setBody(JSON.stringify(obj));
    };

    this.doGet = function () {
        const result = connection.executeQuery('SELECT * FROM "' + ANIMAL_TABLE + '"');
        result.forEach(x => traceErr(JSON.stringify(x)));
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify(result));
    };

    this.doDelete = function (usid) {
        const statement = service.createPreparedDeleteStatement(ANIMAL_TABLE, {aid: aid});
        connection.executeUpdate(statement.sql, statement.aValues);
        connection.commit();
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify({}));
    };
};
