const Animalslib = $.import('xsjs.animals', 'animals').animals;
const animalsLib = new Animalslib($.hdb.getConnection({
    treatDateAsUTC: true
}));

(function () {
    (function handleRequest() {
        try {
            switch ($.request.method) {
                case $.net.http.PUT : {
                    animalsLib.doPut(JSON.parse($.request.body.asString()));
                    break;
                }
                case $.net.http.POST : {
                    animalsLib.doPost(JSON.parse($.request.body.asString()));
                    break;
                }
                case $.net.http.DEL : {
                    animalsLib.doDelete($.request.parameters.get("aid"));
                    break;
                }
                default: {
                    animalsLib.doGet();
                    break;
                }
            }
        } catch (e) {
                $.response.status = $.net.http.BAD_REQUEST;
                $.response.setBody(e.message);
        }
    }());
}());