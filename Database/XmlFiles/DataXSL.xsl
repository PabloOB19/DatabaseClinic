
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html" encoding="UTF-8"/>

    <xsl:template match="/DataBase">
        <html>
            <head>
                <title>Database Clinic</title>
            </head>
            <body>
                <h1>Database Clinic</h1>
                <h2>Summary</h2>

                <table border="1">
                    <tr>
                        <th>Patients</th>
                        <th>Doctors</th>
                        <th>Appointments</th>
                        <th>Surgeries</th>
                        <th>Equipment</th>
                        <th>Roles</th>
                        <th>Users</th>
                    </tr>
                    <tr>
                        <td><xsl:value-of select="count(patient)"/></td>
                        <td><xsl:value-of select="count(doctor)"/></td>
                        <td><xsl:value-of select="count(appointment)"/></td>
                        <td><xsl:value-of select="count(surgery)"/></td>
                        <td><xsl:value-of select="count(equipment)"/></td>
                        <td><xsl:value-of select="count(role)"/></td>
                        <td><xsl:value-of select="count(user)"/></td>
                    </tr>
                </table>

                <h2>Patients</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                    </tr>
                    <xsl:for-each select="patient">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="surname"/></td>
                            <td><xsl:value-of select="email"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Doctors</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Specialty</th>
                    </tr>
                    <xsl:for-each select="doctor">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="surname"/></td>
                            <td><xsl:value-of select="specialty"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>