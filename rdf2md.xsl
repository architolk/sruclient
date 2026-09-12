<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
	xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
  xmlns:eli="http://data.europa.eu/eli/ontology#"
  xmlns:dct="http://purl.org/dc/terms/"
  xmlns:cnt="http://www.w3.org/2011/content#"
>

<xsl:output method="text"/>

<xsl:key name="part" match="/ROOT/rdf:RDF/rdf:Description" use="dct:isPartOf/@rdf:resource"/>
<xsl:key name="expression" match="/ROOT/rdf:RDF/rdf:Description" use="eli:realizes/@rdf:resource"/>
<xsl:key name="item" match="/ROOT/rdf:RDF/rdf:Description" use="@rdf:about"/>

<xsl:variable name="eli">http://data.europa.eu/eli/ontology#</xsl:variable>
<xsl:variable name="eli-LegalResource"><xsl:value-of select="$eli"/>LegalResource</xsl:variable>

<xsl:template match="rdf:Description" mode="regulation">
  <xsl:text># </xsl:text><xsl:value-of select="key('expression',@rdf:about)/dct:title"/><xsl:text>&#xa;</xsl:text>
  <xsl:for-each select="key('expression',key('part',@rdf:about)/@rdf:about)"><xsl:sort select="eli:position" data-type="number"/>
    <xsl:apply-templates select="." mode="subdivision">
      <xsl:with-param name="level">#</xsl:with-param>
    </xsl:apply-templates>
  </xsl:for-each>
</xsl:template>

<xsl:template match="rdf:Description[exists(dct:title)]" mode="subdivision">
  <xsl:param name="level"/>

  <xsl:text>&#xa;</xsl:text><xsl:value-of select="$level"/><xsl:text># </xsl:text><xsl:value-of select="dct:title"/><xsl:text>&#xa;</xsl:text>
  <xsl:if test="exists(cnt:chars)"><xsl:text>&#xa;</xsl:text><xsl:value-of select="cnt:chars"/><xsl:text>&#xa;</xsl:text></xsl:if>
  <xsl:for-each select="key('expression',key('part',key('item',eli:realizes/@rdf:resource)/@rdf:about)/@rdf:about)"><xsl:sort select="eli:position" data-type="number"/>
    <xsl:apply-templates select="." mode="subdivision">
      <xsl:with-param name="level"><xsl:value-of select="$level"/>#</xsl:with-param>
    </xsl:apply-templates>
  </xsl:for-each>
</xsl:template>
<xsl:template match="rdf:Description[not(exists(dct:title))]" mode="subdivision">
  <xsl:param name="level"/>

  <xsl:variable name="listlevel">
    <xsl:choose>
      <xsl:when test="substring($level,1,1)='#'"></xsl:when>
      <xsl:otherwise><xsl:value-of select="$level"/></xsl:otherwise>
    </xsl:choose>
  </xsl:variable>
  <xsl:choose>
    <xsl:when test="exists(eli:number)">
      <xsl:if test="eli:position='1'"><xsl:text>&#xa;</xsl:text></xsl:if>
      <xsl:value-of select="$listlevel"/>
      <xsl:text>- </xsl:text><xsl:value-of select="eli:number"/><xsl:text> </xsl:text>
    </xsl:when>
    <xsl:otherwise>
      <xsl:text>&#xa;</xsl:text>
    </xsl:otherwise>
  </xsl:choose>
  <xsl:value-of select="cnt:chars"/><xsl:text>&#xa;</xsl:text>
  <xsl:for-each select="key('expression',key('part',key('item',eli:realizes/@rdf:resource)/@rdf:about)/@rdf:about)"><xsl:sort select="eli:position[1]" data-type="number"/>
    <xsl:if test="count(eli:position)>1">ERROR</xsl:if>
    <xsl:apply-templates select="." mode="subdivision">
      <xsl:with-param name="level"><xsl:value-of select="$listlevel"/><xsl:text>  </xsl:text></xsl:with-param>
    </xsl:apply-templates>
  </xsl:for-each>
</xsl:template>

<xsl:template match="/">
  <xsl:apply-templates select="ROOT/rdf:RDF/rdf:Description[rdf:type/@rdf:resource=$eli-LegalResource]" mode="regulation"/>
</xsl:template>

</xsl:stylesheet>
