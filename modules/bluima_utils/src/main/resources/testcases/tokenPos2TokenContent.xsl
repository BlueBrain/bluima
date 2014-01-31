<?xml version="1.0" encoding="UTF-8"?>
<!-- ======================================================================
     === XML Stylesheet for conversion of 
     === copyright: EPFL 2011
     === created by: Jean-Cédric Chappelier
     === creation date: November 16th, 2011
     === version: $Revision: 58 $ $Date: 2011-11-29 18:00:59 +0100 (Tue, 29 Nov 2011) $
     ====================================================================== -->
<xsl:stylesheet version="2.0"
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml" encoding="UTF-8" indent="yes"/>
<xsl:preserve-space elements="*"/>

<!-- ======================================================================
     Handling of tokenization/token elements
     ====================================================================== -->
<xsl:include href="tokenPos2TokenContent-core.xsl"/> 

<!-- ======================================================================
     Copy of the rest, unchanged.
     ====================================================================== -->
<xsl:template match="@*">
  <xsl:copy />
</xsl:template>

<xsl:template match="comment()">
   <xsl:copy/>
</xsl:template>

<xsl:template match="*">
  <xsl:element name="{name()}" >
    <xsl:apply-templates select="* | text() | @* | comment()"/>
  </xsl:element>
</xsl:template>

</xsl:stylesheet>
