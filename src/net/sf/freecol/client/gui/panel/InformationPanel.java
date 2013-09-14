/**
 *  Copyright (C) 2002-2013   The FreeCol Team
 *
 *  This file is part of FreeCol.
 *
 *  FreeCol is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  FreeCol is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FreeCol.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.freecol.client.gui.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import net.sf.freecol.client.FreeColClient;
import net.sf.freecol.client.gui.GUI;
import net.sf.freecol.client.gui.panel.MigPanel;
import net.sf.freecol.common.resources.ResourceManager;


/**
 * A general panel for information display.
 */
public class InformationPanel extends FreeColPanel {

    private JPanel textPanel = null;
    private JScrollPane scrollPane = null;


    /**
     * Creates an information panel that shows the given texts and
     * images, and an "OK" button.
     *
     * @param freeColClient The <code>FreeColClient</code> for the game.
     * @param text The text to be displayed in the panel.
     * @param image The image to be displayed in the panel.
     */
    public InformationPanel(FreeColClient freeColClient,
                            String text, ImageIcon image) {
        this(freeColClient, new String[] { text }, new ImageIcon[] { image });
    }

    /**
     * Creates an information panel that shows the given
     * texts and images, and an "OK" button.
     *
     * @param freeColClient The <code>FreeColClient</code> for the game.
     * @param texts The texts to be displayed in the panel.
     * @param images The images to be displayed in the panel.
     */
    public InformationPanel(FreeColClient freeColClient,
                             String[] texts, ImageIcon[] images) {
        super(freeColClient, new MigLayout("wrap 1, insets 200 10 10 10",
                "[510]", "[242]20[20]"));

        textPanel = new MigPanel();
        textPanel.setOpaque(false);
        if (images == null) {
            for (String text : texts) {
                textPanel.add(getDefaultTextArea(text, 30));
            }
        } else {
            textPanel.setLayout(new MigLayout("wrap 2", "", ""));
            for (int i = 0; i < texts.length; i++) {
                if (images[i] == null) {
                    textPanel.add(getDefaultTextArea(texts[i], 30), "skip");
                } else {
                    textPanel.add(new JLabel(images[i]));
                    textPanel.add(getDefaultTextArea(texts[i], 30));
                }
            }
        }

        scrollPane = new JScrollPane(textPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // correct way to make scroll pane opaque
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        setBorder(null);

        add(scrollPane);
        add(okButton, "tag ok");
    }


    // Override JComponent

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(ResourceManager.getImage("InformationPanel.backgroundImage"),
            0, 0, this);
    }


    // Override Component

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNotify() {
        super.removeNotify();
        
        removeAll();
        scrollPane = null;
    }
}