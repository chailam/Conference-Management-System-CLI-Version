# Something you need to know before modifying the csv file
### This txt file will be deleted when submitting the assignment!

## Below are the things you need to take notes when adding stuff to this csv file:
1. All the password stored are **hashed** by **SHA256**. You can simply go to [SHA Generator](https://passwordsgenerator.net/sha256-hash-generator/) to generate a hash. 
Please ensure it is SHA256.

2. While processing the content of file in the software, it use delimiter, which is a comma (,), to separate different values.
Hence, to reduce any issues happened, the value in the csv file should not have any comma (,) , you can use slash (/).
For example, the topicAreas column value can be "Artificial Intelligence/Image Processing/Data Mining & Information Retrieval"

3. The Date entered in the csv file should follow the format "dd/mm/yyyy" to prevent any error happened.