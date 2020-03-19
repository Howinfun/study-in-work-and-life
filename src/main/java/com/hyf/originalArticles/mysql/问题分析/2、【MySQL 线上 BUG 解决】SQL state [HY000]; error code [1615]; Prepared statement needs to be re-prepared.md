
报错：
{
    "code": 1,
    "msg": "查询价格详细报错：\r\n### Error querying database.  Cause: java.sql.SQLException: Prepared statement needs to be re-prepared\r\n### The error may exist in file [D:\\xxx.xml]\r\n### The error may involve cn.gdmcmc.system.biz.mapper.xxxx-Inline\r\n### The error occurred while setting parameters\r\n### SQL:  Cause: java.sql.SQLException: Prepared statement needs to be re-prepared\n; uncategorized SQLException; SQL state [HY000]; error code [1615]; Prepared statement needs to be re-prepared; nested exception is java.sql.SQLException: Prepared statement needs to be re-prepared",
    "data": ""
}

报错前SQL：
```xml
<select id="queryPriceDetail" parameterType="map" resultType="map">
        SELECT
        CONCAT(  DATE_FORMAT( `start_time_point` , '%H:%i' ), '-', CASE WHEN DATE_FORMAT(`end_time_point` , '%H:%i' )='00:00' THEN '24:00' ELSE DATE_FORMAT(`end_time_point` , '%H:%i' ) END  ) AS time,
        CONCAT(0+CAST(elec_price AS CHAR),'') AS elecPrice,
        CONCAT(0+CAST(serv_price AS CHAR),'') AS servPrice,
        CONCAT(0+CAST(charging_price AS CHAR),'') AS chargingPrice,
        CONCAT(0+CAST(agio_elec_price AS CHAR),'') AS agioElecPrice,
        CONCAT(0+CAST(agio_serv_price AS CHAR),'') AS agioServPrice,
        CONCAT(0+CAST(agio_charging_price AS CHAR),'') AS agioChargingPrice,
        flag
        FROM
        mccs.xxx_xxxx_v
        WHERE siteid = #{siteId}
        order by start_time_point
</select>
```
此时MyBatis默认使用 PreparedStatement。

因为都是关于 Prepared，所以改成使用 Statement
```xml
<select id="queryPriceDetail" parameterType="map" resultType="map" statementType="STATEMENT">
    SELECT
    CONCAT(  DATE_FORMAT( `start_time_point` , '%H:%i' ), '-', CASE WHEN DATE_FORMAT(`end_time_point` , '%H:%i' )='00:00' THEN '24:00' ELSE DATE_FORMAT(`end_time_point` , '%H:%i' ) END  ) AS time,
    CONCAT(0+CAST(elec_price AS CHAR),'') AS elecPrice,
    CONCAT(0+CAST(serv_price AS CHAR),'') AS servPrice,
    CONCAT(0+CAST(charging_price AS CHAR),'') AS chargingPrice,
    CONCAT(0+CAST(agio_elec_price AS CHAR),'') AS agioElecPrice,
    CONCAT(0+CAST(agio_serv_price AS CHAR),'') AS agioServPrice,
    CONCAT(0+CAST(agio_charging_price AS CHAR),'') AS agioChargingPrice,
    flag
    FROM
    mccs.xxx_xxxx_v
    WHERE siteid = ${siteId}
    order by start_time_point
</select>
```
不过要注意的是，不能用#{}了，要使用${}。
但是使用${}要注意sql注入的问题。