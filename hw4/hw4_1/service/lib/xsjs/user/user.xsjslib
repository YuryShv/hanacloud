var user = function (connection) {

    const USER_TABLE = "hw3::User";
    /*
            const USER = $.session.securityContext.userInfo.familyName ?
                $.session.securityContext.userInfo.familyName + " " + $.session.securityContext.userInfo.givenName :
                $.session.getUsername().toLocaleLowerCase(),
    */


    this.doPost = function (oUser) {
        //Get Next ID Number
        oUser.usid = getNextval("hw3::usid");

        //generate query
        const statement = createPreparedInsertStatement(USER_TABLE, oUser);
        //execute update
        connection.executeUpdate(statement.sql, statement.aValues);

        connection.commit();
        $.response.status = $.net.http.CREATED;
        $.response.setBody(JSON.stringify(oUser));
    };


    this.doPut = function (obj) {
        const statement = createPreparedUpdateStatement(USER_TABLE, obj);
        //execute update
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
        const statement = createPreparedDeleteStatement(USER_TABLE, {usid: usid});
        connection.executeUpdate(statement.sql, statement.aValues);

        connection.commit();
        $.response.status = $.net.http.OK;
        $.response.setBody(JSON.stringify({}));
    };


    function getNextval(sSeqName) {
        const statement = `select "${sSeqName}".NEXTVAL as "ID" from dummy`;
        const result = connection.executeQuery(statement);

        if (result.length > 0) {
            return result[0].ID;
        } else {
            throw new Error('ID was not generated');
        }
    }

    function createPreparedInsertStatement(sTableName, oValueObject) {
        let oResult = {
            aParams: [],
            aValues: [],
            sql: "",
        };

        let sColumnList = '', sValueList = '';

        Object.keys(oValueObject).forEach(value => {
            sColumnList += `"${value}",`;
            oResult.aParams.push(value);
        });

        Object.values(oValueObject).forEach(value => {
            sValueList += "?, ";
            oResult.aValues.push(value);
        });

        $.trace.error("svalue " + sValueList);
        $.trace.error("scolumn: " + sColumnList);

        sColumnList = sColumnList.slice(0, -1);
        sValueList = sValueList.slice(0, -2);

        oResult.sql = `insert into "${sTableName}" (${sColumnList}) values (${sValueList})`;

        $.trace.error("sql to insert: " + oResult.sql);
        return oResult;
    };

  function createPreparedUpdateStatement(sTableName, oValueObject) {
        let oResult = {
            aParams: [],
            aValues: [],
            sql: "",
        };

        let sColumnList = '', sValueList = '';

        Object.keys(oValueObject).forEach(value => {
            sColumnList += `"${value}",`;
            oResult.aParams.push(value);
        });

        Object.values(oValueObject).forEach(value => {
            sValueList += "?, ";
            oResult.aValues.push(value);
        });

        $.trace.error("svalue " + sValueList);
        $.trace.error("scolumn: " + sColumnList);
        let sWhereClause = '';
        // Remove the last unnecessary comma and blank
        sColumnList = sColumnList.slice(0, -1);
        sValueList = sValueList.slice(0, -2);
        sWhereClause = `"${oResult.aParams[0]}" = '${oResult.aValues[0]}'`;
        sWhereClause = "where " + sWhereClause;
        oResult.sql = `update "${sTableName}" set (${sColumnList}) = (${sValueList}) ${sWhereClause}`;

        $.trace.error("sql to update: " + oResult.sql);
        return oResult;
    };
function createPreparedDeleteStatement(sTableName, oConditionObject) {
        let oResult = {
            aParams: [],
            aValues: [],
            sql: "",
        };

        let sWhereClause = '';
        for (let key in oConditionObject) {
            sWhereClause += `"${key}"=? and `;
            oResult.aValues.push(oConditionObject[key]);
            oResult.aParams.push(key);
        }
        // Remove the last unnecessary AND
        sWhereClause = sWhereClause.slice(0, -5);
        if (sWhereClause.length > 0) {
            sWhereClause = " where " + sWhereClause;
        }

        oResult.sql = `delete from "${sTableName}" ${sWhereClause}`;

        $.trace.error("sql to delete: " + oResult.sql);
        return oResult;
    };
};
