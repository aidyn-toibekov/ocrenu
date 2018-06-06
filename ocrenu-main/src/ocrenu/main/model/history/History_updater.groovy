package ocrenu.main.model.history

import jandcode.dbm.data.*
import jandcode.wax.core.utils.upload.*
import ocrenu.main.model.ocr.Resolver
import ocrenu.main.model.sys.*
import ocrenu.utils.dbfilestorage.*
import org.joda.time.*

class History_updater extends AppUpdaterDao {

    @Override
    DataRecord newRec() throws Exception {
        DataRecord record = super.newRec();
        record.setValue("dte", DateTime.now());
        return record;
    }

    protected void onBeforeSave(DataRecord rec, boolean ins) throws Exception {

        UploadFile uploadFile = (UploadFile) rec.get("fn");
        if (uploadFile) {
            DbFileStorageService fstorage = model.service(DbFileStorageService);
            if (!ins) {
                if (rec.getValueLong("fileStorage") > 0) {
                    DbFileStorageItem oldFile = fstorage.getFile(rec.getValueLong("fileStorage"))
                    String fn = uploadFile.clientFileName.substring(uploadFile.clientFileName.lastIndexOf(String.valueOf("\\")) + 1);
                    if (!oldFile.originalFilename.equals(fn)) {
                        fstorage.removeFile(rec.getValueLong("fileStorage"));
                        addFile(rec);
                    }
                }
            } else {
                addFile(rec)
            }
        }

        if (ins) {
            Resolver resolver = new Resolver(getModel());
            resolver.resolve(rec);
        }


    }

    private void addFile(DataRecord rec) {
        DbFileStorageService fstorage = model.service(DbFileStorageService);
        UploadFile uploadFile = (UploadFile) rec.get("fn");
        if (uploadFile) {
            File f = new File(uploadFile.fileName)
            String fn = uploadFile.clientFileName.substring(uploadFile.clientFileName.lastIndexOf(String.valueOf("\\")) + 1);

            DbFileStorageItem md_file = fstorage.addFile(f, fn)
            rec.set("fileStorage", md_file.getId());
            rec.set("fileName", md_file.getOriginalFilename())
        }
    }

    protected void onAfterDel(long id) {

        DataStore store = ut.loadSql("""
        select * from DbFileStorage f 
        where id not in (select fileStorage from History)
        """);

        DbFileStorageService fstorage = model.service(DbFileStorageService);
        for (DataRecord rec : store) {
            fstorage.removeFile(rec.getValueLong("id"));
        }
    }
}
