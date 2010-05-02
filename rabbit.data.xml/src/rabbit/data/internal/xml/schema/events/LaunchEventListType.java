//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.05.02 at 11:06:06 AM NZST 
//


package rabbit.data.internal.xml.schema.events;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for launchEventListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="launchEventListType">
 *   &lt;complexContent>
 *     &lt;extension base="{}eventGroupType">
 *       &lt;sequence>
 *         &lt;element name="launchEvent" type="{}launchEventType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "launchEventListType", propOrder = {
    "launchEvent"
})
public class LaunchEventListType
    extends EventGroupType
{

    @XmlElement(required = true)
    protected List<LaunchEventType> launchEvent;

    /**
     * Gets the value of the launchEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the launchEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLaunchEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LaunchEventType }
     * 
     * 
     */
    public List<LaunchEventType> getLaunchEvent() {
        if (launchEvent == null) {
            launchEvent = new ArrayList<LaunchEventType>();
        }
        return this.launchEvent;
    }

}
