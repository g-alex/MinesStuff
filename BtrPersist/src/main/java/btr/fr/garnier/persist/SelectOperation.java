/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

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
        } // IDENT: ProjectionList getProjection(ProjectionList , Field)
    }, GROUP {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.groupProperty(field.getName()));
        } // GROUP: ProjectionList getProjection(ProjectionList , Field)
    }, IDGROUP {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.property(field.getName())).
                    add(Projections.groupProperty(field.getName()));
        } // IDGROUP: ProjectionList getProjection(ProjectionList , Field)
    }, MIN {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName()));
        } // MIN: ProjectionList getProjection(ProjectionList , Field)
    }, MAX {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.max(field.getName()));
        } // MAX: ProjectionList getProjection(ProjectionList , Field)
    }, AVG {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.avg(field.getName()));
        } // AVG: ProjectionList getProjection(ProjectionList , Field)
    }, MMA {

        ProjectionList getProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName())).
                    add(Projections.max(field.getName())).
                    add(Projections.avg(field.getName()));
        } // MMA: ProjectionList getProjection(ProjectionList , Field)
    };

    /**
     * Adding a projection among a field on a given projection list.
     *
     * @param projectionList List on which to add the projection.
     * @param field Field on which to apply the projection.
     * @return
     */
    abstract ProjectionList getProjection(ProjectionList projectionList, Field field);
}
