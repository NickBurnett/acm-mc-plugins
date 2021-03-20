package co.acmutd.mc.api.data;

import co.acmutd.mc.api.ACMPlugin;
import co.acmutd.mc.api.util.logger.AnsiColor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class DataFileJSON extends DataFile<JSONObject> {
    public DataFileJSON(final String parent, final String name) {
        super(parent, name, "json");
    }
    @Override
    public void loadData() {
        this.loadFile();
        try (final FileReader reader = new FileReader(this.getDataFile())) {
            this.data = (JSONObject) new JSONParser().parse(reader);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final ParseException e) {
            this.data = new JSONObject();
        } finally {
            this.loadDefaultData();
            this.save();
        }
    }

    @Override
    public void save() {
        this.loadFile();
        try (final FileWriter writer = new FileWriter(this.getDataFile())) {
            if (this.getData() != null && !this.getData().toJSONString().isEmpty()) writer.write(this.getData().toJSONString());
            writer.flush();
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
            if (!this.getData().containsKey(key)) this.getData().put(key, this.getDefaultData().get(key));
        }
    }
}
