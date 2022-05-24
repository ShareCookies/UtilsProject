Apache POI中的HWPF模块是专门用来读取和生成doc格式的文件。
HWPFDocument中的几个概念：
    名称	含义
    HWPFDocument 在HWPF中，我们使用HWPFDocument来表示一个word doc文档。
        创建文档：XWPFDocument doc = new XWPFDocument();
        读取文档：HWPFDocument doc = new HWPFDocument(InputStream);
    Range	表示一个范围，这个范围可以是整个文档，也可以是里面的某个小节（Section），也可以是段落（Paragraph），还可以是拥有功能属性的一段文本（CharacterRun）
    Section	word文档的一个小节，一个word文档可以由多个小节构成。
    Paragraph	word文档的一个段落，一个小节可以由多个段落构成。
    CharacterRun	具有相同属性的一段文本，一个段落可以由多个CharacterRun组成。
    Table	一个表格。
    TableRow	表格对应的行
    TableCell	表格对应的单元格
    注意 ： Section、Paragraph、CharacterRun和Table都继承自Range。
官网参考手册：
    https://poi.apache.org/components/index.html




https://www.cnblogs.com/grasslucky/p/10613308.html
https://www.jianshu.com/p/8d23b7f54b8e
https://blog.csdn.net/m0_38022029/article/details/84350433