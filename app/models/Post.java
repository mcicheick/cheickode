package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Created by sissoko on 02/05/2016.
 */
@Entity
@Table(name = "T_POSTS")
public class Post extends ModelBase {

    public static final Finder<Long, Post> find = new Finder<>(Post.class);

    @Id
    @Column(name = "ID")
    public Long id;

    @Column(name = "TITLE")
    @Constraints.Required
    public String title;

    @Lob
    @Column(name = "PROBLEM")
    @Constraints.Required
    public String problem;

    @Lob
    @Column(name = "SOLUTION")
    public String solution;

    @Override
    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public ObjectNode toJson() {
        ObjectNode result = super.toJson();
        result
                .put("title", title)
                .put("problem", problem)
                .put("solution", solution)
        ;
        return result;
    }
}
