/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.btrpersist;

import java.lang.reflect.Field;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 *
 * @author agarnier
 */
public enum SelectOperation {

    IDENT {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.property(field.getName()));
        }
    }, GROUP {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.groupProperty(field.getName()));
        }
    }, IDGROUP {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.property(field.getName())).
                    add(Projections.groupProperty(field.getName()));
        }
    }, MIN {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName()));
        }
    }, MAX {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.max(field.getName()));
        }
    }, AVG {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.avg(field.getName()));
        }
    }, MMA {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName())).
                    add(Projections.max(field.getName())).
                    add(Projections.avg(field.getName()));
        }
    };

    abstract ProjectionList getProjection(ProjectionList projectionList, Field field);
}
