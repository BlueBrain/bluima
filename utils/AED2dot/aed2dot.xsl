<?xml version="1.0" encoding="UTF-8"?>
<!-- ======================================================================
     === XML Stylesheet for conversion of Apache UIMA Analysis Engine 
     ==    Description (AED) to dot graph language.
     === copyright: EPFL 2011
     === created by: Jean-Cédric Chappelier
     === creation date: December 20th, 2011
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
<xsl:template match="rs:frameworkImplementation"/>
<xsl:template match="rs:primitive"/>
<xsl:template match="rs:delegateAnalysisEngineSpecifiers"/>
<xsl:template match="rs:operationalProperties"/>

<!-- ======================================================================
     Handling of ...
     ====================================================================== -->
<xsl:template match="/rs:analysisEngineDescription">
    <xsl:apply-templates/>
</xsl:template>

<xsl:template match="rs:fixedFlow">
  <xsl:text>digraph AED {
  node [ shape = rectangle, style = filled, color = "/blues9/2" ];
  </xsl:text>
  <xsl:apply-templates/>
  <xsl:text>
}
</xsl:text>
</xsl:template>

<xsl:template match="rs:node">
    <xsl:if test="position()!=1">
      <xsl:text>
  -&gt; </xsl:text>
    </xsl:if>
    <xsl:text>"</xsl:text>
    <xsl:apply-templates select="text()"/>
    <xsl:text>"</xsl:text>
</xsl:template>

<!-- ======================================================================
     Copy of the rest, unchanged.
     ====================================================================== -->
<xsl:template match="@*">
  <xsl:copy />
</xsl:template>

<xsl:template match="comment()">
   <xsl:copy/>
</xsl:template>

<xsl:template match="*" >
  <xsl:element name="{name()}" >
    <xsl:apply-templates select="* | text() | @* | comment()"/>
  </xsl:element>
</xsl:template>

</xsl:stylesheet>
