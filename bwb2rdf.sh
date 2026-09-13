# Transform to RDF
java -jar lib/saxon9.jar -s:data/BWBR0015703.xml -xsl:bwb2rdf.xsl -o:data/BWBR0015703.rdf
# Convert XML/RDF to Turtle and JSON-LD
java -jar lib/rdf2rdf.jar -i data/BWBR0015703.rdf -o data/BWBR0015703.ttl
java -jar lib/rdf2rdf.jar -i data/BWBR0015703.rdf -o data/BWBR0015703.json
# Sanity check: convert back to text (markdown)
java -jar lib/rdf2xml.jar data/BWBR0015703.rdf data/BWBR0015703.md rdf2md.xsl
# Create SHACL fiel for data structure
#java -jar lib/rdf2rdf.jar -i data/BWBR0015703.rdf -o data/model.ttl -c lib/rdf2sh.yaml
java -jar lib/rdf2xml.jar data/model.ttl data/model.graphml lib/rdf2graphml.xsl add data/model-edited.graphml
