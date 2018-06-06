package ocrenu.utils.dbfilestorage;

import java.io.*;

/**
 * Представление файла из хранилища
 */
public abstract class DbFileStorageItem {

    /**
     * id файла
     */
    public abstract long getId();

    /**
     * Физический файл в хранилище
     */
    public abstract File getFile();

    /**
     * Оригинальное имя файла, с которым файл был помещен в хранилище
     */
    public abstract String getOriginalFilename();

}
