package ocrenu.main.model.history

import jandcode.dbm.sqlfilter.*
import jandcode.wax.core.model.*

class History_list extends WaxLoadSqlFilterDao {

    History_list() {
        domainResult = "History"
    }

    protected void onCreateFilter(SqlFilter f) throws Exception {

        //
        f.paginate = true
        //
        f.sql = "select t.* from History t where 0=0 order by t.id"
    }

}
