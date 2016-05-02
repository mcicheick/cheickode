package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The superclass for all models.
 * @author Djamma Dev.
 *
 * @date 2015-11-26 20:11:56
 */
@MappedSuperclass
public abstract class ModelBase extends Model {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    /**
     * The date when the model was created.
     */
    @Column(name = "CREATE_DATE", nullable = true, columnDefinition = "DATETIME DEFAULT NULL")
    public Date create_date;
    
    /**
     * The date of the last modification.
     * It is set to current date on every call to
     *
     * @see models.ModelBase#save
     */
    @Column(name = "MODIFY_DATE", nullable = true, columnDefinition = "DATETIME DEFAULT NULL")
    public Date modify_date;
    
    public ModelBase() {
        create_date = new Date();
        modify_date = new Date();
    }

    /**
     * Override Model#save method to set the modification date to the current date.
     * @see Model#save()
     */
    @Override
    public void save() {
        this.modify_date = new Date();
        super.save();
    }

    public abstract Object getId();

    public ObjectNode toJson() {
        ObjectNode result = Json.newObject();
        if(getId() != null) {
            result.put("id", getId().toString());
        }
        result.put("create_date", sdf.format(create_date));
        result.put("modify_date", sdf.format(modify_date));
        return result;
    }

}
