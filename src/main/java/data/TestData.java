package data;

public class TestData {

    public static final String xmlPath(String fileName){
        String currentDirectory = System.getProperty("user.dir");
        return currentDirectory+"/src/test/xmlFiles/"+fileName;
    }

    public static final String invalidZipFile = "InvalidZipCode_createQuote.xml";
    public static final String invalidAddressFile = "InvalidAddressId_createQuote.xml";
    public static final String validQuoteFile = "createQuote.xml";
    public static final String validQuoteFile_CA2US = "createValidQuote_CA2US.xml";
}
