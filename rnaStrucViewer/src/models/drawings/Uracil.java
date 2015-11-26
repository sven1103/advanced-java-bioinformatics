package models.drawings;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;

/**
 * advanced-java-bioinformatics
 *
 * Description:
 *  <- content ->
 *
 * @author fillinger
 * @version
 * Date: 11/26/15
 * EMail: sven.fillinger@student.uni-tuebingen.de
 */
public class Uracil extends AbstractNucleotideCircle {

    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    public Uracil(){
        super();
        this.setColor(Color.rgb(163,179,54));
        setTooltip("U");
        setBaseType();
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }

    @Override
    protected void setBaseType() {
        this.base.setText("U");
    }

    @Override
    protected void setTooltip(String message) {
        this.tooltip = new Tooltip(message);
        tooltip.autoHideProperty().setValue(false);
        Tooltip.install(this.toolTipMask, this.tooltip);
    }


}
