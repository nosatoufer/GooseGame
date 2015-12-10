/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heb.esi.goosegame.view;

import java.io.Serializable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Maxime
 */
public class CustomRectangle extends Rectangle implements Serializable {
    protected ObjectProperty<Color> color;
    
    public CustomRectangle(int width, int height, Color color) {
        super(width, height, color);

        // Propriété prise en compte par le bean (couleur du joueur présent
        // sur la case)
        this.color = new ObjectPropertyBase<Color>(null) {
            @Override
            public Object getBean() {
                return this;
            }

            @Override
            public String getName() {
                return "Color property";
            }
        };
        
        this.color.set(color);
    }

    /**
     * Retourne la couleur du joueur présent sur la case
     *
     * @return Couleur du joueur présent sur la case
     */
    public final Color getColor() {
        return this.color.get();
    }

    /**
     * On place le joueur de couleur color sur la case
     *
     * @param color
     */
    public final void setColor(Color color) {
        this.color.set(color);
        this.setFill(color);
        if (!color.equals(Color.TRANSPARENT)) {
            this.setStroke(Color.BLACK);
        } else {
            this.setStroke(color);
        }
    }

    /**
     * Méthode du beans permettant d'attacher un listener sur l'attribut
     * PlayerColor
     *
     * @return ObjectProperty
     */
    public final ObjectProperty<Color> colorProperty() {
        return this.color;
    }
}
