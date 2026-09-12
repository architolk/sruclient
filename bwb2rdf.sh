# Transform to RDF
java -jar lib/saxon9.jar -s:data/BWBR0015703.xml -xsl:bwb2rdf.xsl -o:data/BWBR0015703.rdf
# Convert XML/RDF to Turtle
java -jar lib/rdf2rdf.jar -i data/BWBR0015703.rdf -o data/BWBR0015703.ttl
# Sanity check: convert back to text (markdown)
java -jar lib/rdf2xml.jar data/BWBR0015703.rdf data/BWBR0015703.md rdf2md.xsl
