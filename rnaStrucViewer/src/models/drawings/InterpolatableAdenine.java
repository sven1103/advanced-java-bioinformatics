package models.drawings;

import javafx.animation.Interpolatable;

/**
 * advanced-java-bioinformatics
 * <p>
 * Description:
 * <- content ->
 *
 * @author fillinger
 * @version Date: 11/27/15
 *          EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class InterpolatableAdenine
        extends Adenine
        implements Interpolatable<InterpolatableAdenine>{

    public InterpolatableAdenine(double x, double y){
        super(x, y);
    }

    public InterpolatableAdenine(){}

    @Override
    public InterpolatableAdenine interpolate(InterpolatableAdenine endValue, double t) {
        return (new InterpolatableAdenine(
                this.getLayoutX() + t * (endValue.getX() - this.getLayoutX()),
                this.getLayoutY() + t * (endValue.getY() - this.getLayoutY())));
    }
}
