package com.acmutd.mc.api.data;

import com.acmutd.mc.api.ACMPlugin;
import me.nickaburnett.logger.AnsiColor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class DataFile<D> {
    private final File file;
    protected D data;

    /**
     * Constructs a DataFile in a given parent directory.
     * @param parent Parent Directory
     * @param name File Name
     * @param extension File Extension
     */
    public DataFile(final String parent, final String name, final String extension) {
        this.file = new File(parent, name + '.' + extension);
        if ((new File(parent)).mkdirs()) ACMPlugin.getInstance().logger().info("Creating directory _0_...", AnsiColor.GREEN.getForeground() + parent);
        this.data = null;
    }
    public final File getDataFile() {
        return this.file;
    }
    public final void loadFile() {
        if (this.getDataFile().exists()) return;
        try {
            this.getDataFile().createNewFile();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    public final D getData() {
        return this.data;
    }

    public abstract void loadData();
    public abstract void save();
    public abstract void loadDefaultData();
    public abstract Map<String, Object> getDefaultData();
}
