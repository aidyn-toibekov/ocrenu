<%@ page import="ocrenu.main.utils.ExpAppService; jandcode.auth.AuthService; ocrenu.utils.dbinfo.DbInfoService; jandcode.utils.*; jandcode.web.*; jandcode.dbm.*; jandcode.dbm.data.*; jandcode.wax.core.utils.gf.*" %>
%{-- 


========================================================================= --}%

<gf:groovy>
  <%
    GfFrame gf = args.gf
    GfAttrs a = gf.attrs
    //
    def expApp = gf.app.service(ExpAppService.class).getCurrentExpApp()
    a.appIcon = expApp.icon
    a.appTitle = expApp.title
    def usr = gf.app.service(AuthService.class).currentUser;
    a.fn = usr.fullname;

    // информация о базе
    def model = gf.model
    a.dbName = model.name
    a.dbDriver = model.dbSource.dbType
    a.dbUrl = model.dbSource.url
    a.dbInfo = model.dbSource.database
    if ("oracle".equals(a.dbDriver)) {
      a.dbInfo = model.dbSource.username
    }

    a.dbId = model.service(DbInfoService).getDbId()

    // информация о приложении
    a.versionApp = new VersionInfo("ocr.main").version
  %>
</gf:groovy>

%{-- ========================================================================= --}%
<g:javascript>
  //
  th.width = 600;
  th.shower = "dialogclose";
  th.title = UtLang.t("О программе...");
  th.layout = b.layout('vbox');


  //
  this.items = [
    b.pageheader(UtLang.t("Система распознования номеров").bold(), Jc.url("page/logo.png")),
    b.box({
      layout: b.layout("vbox"),
      items: [
        b.panel({
          flex: 1,
          border:false,
          layout: b.layout("vbox"),
          items: [
            b.databox({
              items: [
                b.delim(UtLang.t("Информация о базе данных модели")),
                b.label(UtLang.t("Имя модели")),
                b.datalabel(th.dbName),
                b.label(UtLang.t("ID модели")),
                b.datalabel(th.dbId),
                b.label(UtLang.t("Драйвер")),
                b.datalabel(th.dbDriver),
                b.label(UtLang.t("url базы")),
                b.datalabel(th.dbUrl),
                b.label(UtLang.t("База данных")),
                b.datalabel(th.dbInfo)

                ,
                b.delim(UtLang.t("Информация о версии приложения")),
                b.label(UtLang.t("Версия")),
                b.datalabel(th.versionApp)
              ]
            })
          ]
        }),
        b.datalabel(UtLang.t("Автор:")),
        b.datalabel(UtLang.t("Выпускница ЕНУ им Гумилева").bold()),
        b.datalabel(UtLang.t("(c) Тастанбек Минара").bold()),
        b.datalabel(UtLang.t("Астана - 2018").bold())
      ]
    })
  ];


</g:javascript>
