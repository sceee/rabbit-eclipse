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
 * <p>Java class for fileEventListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fileEventListType">
 *   &lt;complexContent>
 *     &lt;extension base="{}eventGroupType">
 *       &lt;sequence>
 *         &lt;element name="fileEvent" type="{}fileEventType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fileEventListType", propOrder = {
    "fileEvent"
})
public class FileEventListType
    extends EventGroupType
{

    @XmlElement(required = true)
    protected List<FileEventType> fileEvent;

    /**
     * Gets the value of the fileEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileEventType }
     * 
     * 
     */
    public List<FileEventType> getFileEvent() {
        if (fileEvent == null) {
            fileEvent = new ArrayList<FileEventType>();
        }
        return this.fileEvent;
    }

}
