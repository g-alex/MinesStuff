/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btr.fr.garnier.persist;

import java.lang.reflect.Field;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * Relevant fields selections.
 *
 * @author agarnier
 */
public enum Selection {

    IDENT {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.property(field.getName()));
        } // IDENT: ProjectionList addProjection(ProjectionList , Field)
    }, GROUP {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.groupProperty(field.getName()));
        } // GROUP: ProjectionList addProjection(ProjectionList , Field)
    }, IDGROUP {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.property(field.getName())).
                    add(Projections.groupProperty(field.getName()));
        } // IDGROUP: ProjectionList addProjection(ProjectionList , Field)
    }, MIN {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName()));
        } // MIN: ProjectionList addProjection(ProjectionList , Field)
    }, MAX {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.max(field.getName()));
        } // MAX: ProjectionList addProjection(ProjectionList , Field)
    }, AVG {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.avg(field.getName()));
        } // AVG: ProjectionList addProjection(ProjectionList , Field)
    }, MMA {

        ProjectionList addProjection(ProjectionList projectionList, Field field) {
            return projectionList.add(Projections.min(field.getName())).
                    add(Projections.max(field.getName())).
                    add(Projections.avg(field.getName()));
        } // MMA: ProjectionList addProjection(ProjectionList , Field)
    };

    /**
     * Adding a projection among a field on a given projection list.
     *
     * @param projectionList List on which to add the projection.
     * @param field Field on which to apply the projection.
     * @return
     */
    abstract ProjectionList addProjection(ProjectionList projectionList, Field field);
}
