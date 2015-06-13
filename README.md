uwrite
======

### 部署说明

#### 初始数据库

复制 `src/main/resources` 目录下的 `persistence.properties.default` 为 `persistence.properties`，并打开你新复制出来的 `persistence.properties`，修改其中的数据库配置：

`dataSource.driverClassName`：如果你使用 MySQL 以外的数据库，需要修改这个
`dataSource.url`：指定数据库名，默认为 uwrite，如果使用默认值，请先建立一个空数据库 uwrite
`dataSource.username`：数据库连接用户名
`dataSource.password`：数据库连接密码

请不要直接修改 `persistence.properties.default`，这是配置样例。
你的个人配置不会发布到项目仓库中。

#### 图片目录配置

uwrite 中，图片目录是独立在项目以外的。请找个合适的位置（例如，可读写，并且路径中没有中文）创建图片目录，并进行配置：

复制 `src/main/resources` 目录下的 `config.properties.default` 为 `config.properties`，并修改你新复制出来的 `config.properties`，将其中的路径改为你图片目录的路径。

请不要直接修改 `config.properties.default`，这是配置样例。
你的个人配置不会发布到项目仓库中。