package com.prosthetics.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete, custom-designed prosthetic limb.
 *
 * <p>This class plays two roles in the design patterns applied in this project:
 * <ul>
 *   <li><b>Product</b> of the {@link com.prosthetics.builder.ProsthesisBuilder} (Builder pattern),
 *       which assembles it step by step from its optional/mandatory components.</li>
 *   <li><b>Concrete Prototype</b> of the Prototype pattern: instances can be
 *       {@link #clone() cloned} to quickly create new, independent configurations
 *       starting from an existing clinical template.</li>
 * </ul>
 *
 * <p>Only {@link com.prosthetics.builder.ProsthesisBuilder} and
 * {@link com.prosthetics.prototype.ProsthesisTemplateRegistry} are meant to
 * create or clone instances; the no-args package-private constructor keeps
 * external code from bypassing those two entry points.
 */
public class Prosthesis implements Cloneable {

    private String limbType;          // e.g. "Transfemoral", "Transtibial", "Transradial"
    private Socket socket;
    private JointSystem joint;
    private List<Sensor> sensors = new ArrayList<>();
    private PowerSource powerSource;  // may be null for fully passive/mechanical devices
    private CosmeticCover cover;

    public Prosthesis() {
        // Intentionally empty: populated by ProsthesisBuilder or by clone().
    }

    // --- Getters ---

    public String getLimbType() {
        return limbType;
    }

    public Socket getSocket() {
        return socket;
    }

    public JointSystem getJoint() {
        return joint;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public PowerSource getPowerSource() {
        return powerSource;
    }

    public CosmeticCover getCover() {
        return cover;
    }

    // --- Setters (package-visible; only the Builder should call these directly) ---

    public void setLimbType(String limbType) {
        this.limbType = limbType;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setJoint(JointSystem joint) {
        this.joint = joint;
    }

    public void setPowerSource(PowerSource powerSource) {
        this.powerSource = powerSource;
    }

    public void setCover(CosmeticCover cover) {
        this.cover = cover;
    }

    /**
     * Deep-clones this prosthesis: every mutable component is cloned
     * individually so the copy is fully independent from the original.
     * This is the core operation of the Prototype pattern in this project.
     */
    @Override
    public Prosthesis clone() {
        Prosthesis copy = new Prosthesis();
        copy.limbType = this.limbType;
        copy.socket = (this.socket != null) ? this.socket.clone() : null;
        copy.joint = (this.joint != null) ? this.joint.clone() : null;

        copy.sensors = new ArrayList<>();
        for (Sensor sensor : this.sensors) {
            copy.sensors.add(sensor.clone());
        }

        copy.powerSource = (this.powerSource != null) ? this.powerSource.clone() : null;
        copy.cover = (this.cover != null) ? this.cover.clone() : null;
        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prosthesis [").append(limbType != null ? limbType : "Unspecified limb").append("]\n");
        sb.append("  - ").append(socket != null ? socket : "No socket configured").append("\n");
        sb.append("  - ").append(joint != null ? joint : "No joint system configured").append("\n");
        sb.append("  - Power source: ").append(powerSource != null ? powerSource : "None (passive device)").append("\n");
        sb.append("  - Sensors: ");
        if (sensors.isEmpty()) {
            sb.append("None");
        } else {
            for (int i = 0; i < sensors.size(); i++) {
                sb.append(sensors.get(i));
                if (i < sensors.size() - 1) sb.append(", ");
            }
        }
        sb.append("\n  - ").append(cover != null ? cover : "No cosmetic cover configured");
        return sb.toString();
    }
}
