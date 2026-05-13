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
                        <th>Sex</th>
                        <th>Date of Birth</th>
                        <th>Height</th>
                        <th>Weight</th>
                        <th>Info</th>
                    </tr>
                    <xsl:for-each select="patient">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="surname"/></td>
                            <td><xsl:value-of select="email"/></td>
                            <td><xsl:value-of select="sex"/></td>
                            <td><xsl:value-of select="dob"/></td>
                            <td><xsl:value-of select="height"/></td>
                            <td><xsl:value-of select="weight"/></td>
                            <td><xsl:value-of select="info"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Doctors</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Email</th>
                        <th>Sex</th>
                        <th>Date of Birth</th>
                        <th>Specialty</th>
                    </tr>
                    <xsl:for-each select="doctor">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="surname"/></td>
                            <td><xsl:value-of select="email"/></td>
                            <td><xsl:value-of select="sex"/></td>
                            <td><xsl:value-of select="dob"/></td>
                            <td><xsl:value-of select="specialty"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Appointments</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Date</th>
                        <th>Price</th>
                        <th>Turn</th>
                    </tr>
                    <xsl:for-each select="appointment">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="type"/></td>
                            <td><xsl:value-of select="date"/></td>
                            <td><xsl:value-of select="price"/></td>
                            <td><xsl:value-of select="turn"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Surgeries</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Date</th>
                        <th>Price</th>
                        <th>Turn</th>
                    </tr>
                    <xsl:for-each select="surgery">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="type"/></td>
                            <td><xsl:value-of select="date"/></td>
                            <td><xsl:value-of select="price"/></td>
                            <td><xsl:value-of select="turn"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Equipment</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Expiration Date</th>
                    </tr>
                    <xsl:for-each select="equipment">
                        <tr>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="category"/></td>
                            <td><xsl:value-of select="quantity"/></td>
                            <td><xsl:value-of select="price"/></td>
                            <td><xsl:value-of select="expiration_date"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Roles</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                    </tr>
                    <xsl:for-each select="role">
                        <tr>
                            <td><xsl:value-of select="@roleId"/></td>
                            <td><xsl:value-of select="name"/></td>
                        </tr>
                    </xsl:for-each>
                </table>

                <h2>Users</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                    </tr>
                    <xsl:for-each select="user">
                        <tr>
                            <td><xsl:value-of select="@userId"/></td>
                            <td><xsl:value-of select="username"/></td>
                            <td><xsl:value-of select="email"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
