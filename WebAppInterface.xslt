<?xml version="1.0" encoding="UTF-8"?>
<xsl:transform version="1.0"
               xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <html>
            <body>
                <h2>RESTController</h2>
                <table border="1">
                    <tr>
                        <th bgcolor="yellow">Operation</th>
                        <th bgcolor="yellow">Arguments(s)</th>
                        <th bgcolor="yellow">Return</th>

                    </tr>
                    <xsl:for-each select="interface/abstract_method">
                        <tr>
                            <td>
                                <b>
                                    <xsl:value-of select="@name"/>
                                </b>
                            </td>
                            <td>
                                <xsl:for-each select="arguments/parameter">
                                    <xsl:value-of select="current()"/>:<xsl:value-of select="@type"/>,
                                </xsl:for-each>
                            </td>
                            <td><xsl:value-of select="return"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:transform>
