<?xml version="1.0" encoding="UTF-8"?>
<!-- ======================================================================
     === XML Stylesheet for conversion of Apache UIMA Collection Processing
     === Engine Description (CPED) to dot graph language.
     === copyright: EPFL 2012
     === created by: Jean-Cédric Chappelier
     === creation date: September 3rd, 2012
     === version: $Revision$ $Date$
     ====================================================================== -->
<xsl:stylesheet version="2.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rs="http://uima.apache.org/resourceSpecifier">

<xsl:output method="text" encoding="UTF-8" indent="yes"/>
<xsl:strip-space elements="*"/>

<!-- ======================================================================
     Delete nodes
     ====================================================================== -->
<xsl:template match="rs:cpeConfig"/>
<xsl:template match="rs:frameworkImplementation"/>
<xsl:template match="rs:processingResourceMetaData"/>

<!-- ======================================================================
     noEpflPrefix template

     removes 'ch.epfl.bbp.uima.' prefix if it exists.

     ====================================================================== -->
<xsl:template name="noEpflPrefix">
    <xsl:param name="form"/>
    <xsl:choose>
      <xsl:when test="starts-with($form, 'ch.epfl.bbp.uima.')">
        <xsl:value-of select="substring-after($form, 'ch.epfl.bbp.uima.')"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$form"/>
      </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!-- ======================================================================
     Handling of ...
     ====================================================================== -->
<xsl:template match="/rs:cpeDescription">
  <xsl:text>digraph CPED {
  node [ shape = rectangle, style = filled, color = "/blues9/2" ];
  </xsl:text>
    <xsl:apply-templates/>
  <xsl:text>
}
</xsl:text>
</xsl:template>

<xsl:template match="rs:collectionReaderDescription/rs:implementationName">
    <xsl:text>"</xsl:text>
    <xsl:call-template name="noEpflPrefix">
      <xsl:with-param name="form" select="."/>
    </xsl:call-template>
    <xsl:text>" [ shape = oval ];</xsl:text>
    <xsl:if test="position()!=last()">
    <xsl:text>
"</xsl:text>
    <xsl:call-template name="noEpflPrefix">
      <xsl:with-param name="form" select="."/>
    </xsl:call-template>
    <xsl:text>" -&gt; </xsl:text>
    </xsl:if>
</xsl:template>

<xsl:template match="rs:casProcessor">
    <xsl:if test="position()!=1">
      <xsl:text>
  -&gt; </xsl:text>
    </xsl:if>  
    <xsl:text>"</xsl:text>
    <xsl:call-template name="noEpflPrefix">
      <xsl:with-param name="form" select="@name"/>
    </xsl:call-template>
    <xsl:text>"</xsl:text>
</xsl:template>

</xsl:stylesheet>
