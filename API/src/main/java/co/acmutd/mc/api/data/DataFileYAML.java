package co.acmutd.mc.api.data;

import co.acmutd.mc.api.ACMPlugin;
import co.acmutd.mc.api.util.logger.AnsiColor;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class DataFileYAML extends DataFile<Map<String, Object>> {
    public DataFileYAML(final String parent, final String name) {
        super(parent, name, "yml");
    }

    @Override
    public void loadData() {
        this.loadFile();
        try (final FileReader reader = new FileReader(this.getDataFile())) {
            this.data = (new Yaml()).load(reader);
            this.data = (this.data == null) ? new HashMap<>() : this.data;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            this.loadDefaultData();
            this.save();
        }
    }

    @Override
    public void save() {
        this.loadFile();
        try (final FileWriter writer = new FileWriter(this.getDataFile())) {
            this.loadDefaultData();
            (new Yaml()).dump(this.data, writer);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDefaultData() {
        if (this.getData() == null) {
            ACMPlugin.getInstance().logger().warn("Unable to load raw data for datafile _0_...", AnsiColor.GREEN.getForeground() + this.getDataFile().getName());
            return;
        }
        for (final String key : this.getDefaultData().keySet()) {
            if (!this.getData().containsKey(key)) this.data.put(key, this.getDefaultData().get(key));
        }
    }
}
