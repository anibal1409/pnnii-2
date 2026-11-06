package pnni.laboratorio.ui;

import java.awt.Font;
import java.awt.Insets;
import java.util.Map;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;
import com.formdev.flatlaf.fonts.jetbrains_mono.FlatJetBrainsMonoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

/** Configuración global del look & feel (FlatLaf + tipografías Inter y JetBrains Mono). */
public final class Tema {

    private Tema() {
    }

    public static void instalarFuentes() {
        FlatInterFont.install();
        FlatJetBrainsMonoFont.install();
        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY);
        FlatLaf.setPreferredLightFontFamily(FlatInterFont.FAMILY_LIGHT);
        FlatLaf.setPreferredSemiboldFontFamily(FlatInterFont.FAMILY_SEMIBOLD);
        FlatLaf.setPreferredMonospacedFontFamily(FlatJetBrainsMonoFont.FAMILY);
    }

    /** Aplica el tema; si ya hay ventanas abiertas, las actualiza en caliente. */
    public static void aplicar(boolean oscuro) {
        Paleta p = oscuro ? Paleta.OSCURA : Paleta.CLARA;
        Paleta.usar(p);
        FlatLaf.setGlobalExtraDefaults(Map.of(
                "@accentColor", Paleta.css(p.acento()),
                "@background", Paleta.css(p.fondo())));

        UIManager.put("Component.arc", 10);
        UIManager.put("Button.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("ScrollBar.width", 10);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("TabbedPane.showTabSeparators", false);
        UIManager.put("TabbedPane.tabHeight", 34);
        UIManager.put("TabbedPane.selectedBackground", p.superficie());
        UIManager.put("Slider.thumbSize", new java.awt.Dimension(14, 14));
        UIManager.put("Slider.trackWidth", 4);
        UIManager.put("ToolTip.background", p.superficieAlta());

        if (oscuro) {
            FlatMacDarkLaf.setup();
        } else {
            FlatMacLightLaf.setup();
        }
        FlatLaf.updateUI();
    }

    public static Font fuente(int estilo, float tamano) {
        return UIManager.getFont("defaultFont").deriveFont(estilo, tamano);
    }

    public static Font mono(float tamano) {
        return new Font(FlatJetBrainsMonoFont.FAMILY, Font.PLAIN, Math.round(tamano)).deriveFont(tamano);
    }
}
