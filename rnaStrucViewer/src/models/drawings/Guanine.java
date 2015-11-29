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
public class Guanine extends AbstractNucleotideCircle {

    public Guanine(){
        super();
        this.setColor(Color.rgb(43,79,120));
        setTooltip("G");
        setBaseType();
    }


    @Override
    protected void setBaseType() {
        this.base.setText("G");
    }



}
