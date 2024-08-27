<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  xmlns:sh="http://www.w3.org/ns/shacl#"
  xmlns:skos="http://www.w3.org/2004/02/skos/core#"
  xmlns:dct="http://purl.org/dc/terms/"
>

<xsl:output method="text"/>

<xsl:template match="/">
  <!--
  <xsl:for-each-group select="toestanden/toestand" group-by="@type">
    <xsl:value-of select="@type"/>
    <xsl:text>&#x0a;</xsl:text>
  </xsl:for-each-group>
-->
  <xsl:for-each select="toestanden/toestand[@type='rijkswet' or @type='wet' or @type='KB' or @type='rijksKB' or @type='ministeriele-regeling' or @type='AMvB' or @type='zbo' or @type='pbo' or @type='ministeriele-regeling-archiefselectielijst']">
    <xsl:text>cp 2024-01-01/</xsl:text>
    <xsl:value-of select="@identifier"/><xsl:text>.txt</xsl:text>
    <xsl:text> selectie</xsl:text>
    <xsl:text>&#x0a;</xsl:text>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet>
