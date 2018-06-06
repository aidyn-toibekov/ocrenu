package ocrenu.utils.dbinfo.impl;

import ocrenu.utils.dbinfo.*;
import ocrenu.utils.dbinfo.model.*;
import jandcode.dbm.data.*;
import jandcode.utils.*;
import jandcode.utils.error.*;

public class DbInfoServiceImpl extends DbInfoService {

    protected String dbid;

    public String getDbId() {
        if (dbid == null) {
            synchronized (this) {
                if (dbid == null) {
                    DbInfo_utils dao = (DbInfo_utils) getModel().createDao("DbInfo/utils");
                    try {
                        DataRecord r = dao.loadRec();
                        String s = r.getValueString("dbid");
                        if (UtString.empty(s)) {
                            s = dao.updateDbid();
                        }
                        dbid = s;
                    } catch (Exception e) {
                        throw new XErrorWrap(e);
                    }
                }
            }
        }
        return dbid;
    }

}
