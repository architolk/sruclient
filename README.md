# sruclient
Client for the SRU protocol and parsing of BWB

Usage:

```
java -jar sruclient [dump|xml|txt|meta]
```

- `dump`: dump the records as-is
- `xml`: create curl commands that will download the XML version of the legislation
- `txt`: create curl commands that will download the TXT version of the legislation
- `meta`: create an XML file that contains the metadata of the a particular regulation
