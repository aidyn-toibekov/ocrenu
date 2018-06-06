<%@ page import="ocrenu.utils.dbinfo.DbInfoService; ocrenu.main.utils.ExpAppService; jandcode.auth.AuthService; jandcode.utils.*; jandcode.web.*; jandcode.dbm.*; jandcode.dbm.data.*; jandcode.wax.core.utils.gf.*" %>
%{-- 

Home page

========================================================================= --}%

<gf:groovy>
  <%
    GfFrame gf = args.gf
    GfAttrs a = gf.attrs
    //
    SGridDataStore st = gf.createSGridStore("History/list")
    st.load();
    a.store = st;

  %>
</gf:groovy>

%{-- ========================================================================= --}%
<g:javascript>
  //
  th.title = UtLang.t("Распознование номеров");
  th.layout = b.layout('vbox');

  th.frameRec = "js/platform/history/history_rec.gf";
  th.daoUpdater = "History/updater";

  //
  var sgrid = b.sgrid({
    flex: 1,
    //
    store: th.store,
    refreshRec: true,
    paginate: true,
    autoScroll: true,
    //
    columns: function(b) {
      return [
        b.column('description', {tdCls: "td-wrap", flex: 1, title: UtLang.t("Распозанный текст")}),
        b.column('dte', {tdCls: "td-wrap", width: 130}),
        b.column('fileName', {tdCls: "td-wrap", width: 130}),
        b.column('cmt', {tdCls: "td-wrap", width: 130})
      ];
    },
    //
    actions: function(b) {
        return [
        b.actionInsFrame({
          text:UtLang.t("Загрузить и распознать"),
          frame: th.frameRec,
          onOk: function(fr) {
            sgrid.reload();
          }
        }),
        b.actionUpdFrame({
          frame: th.frameRec, disabled: true, itemId: "UPD"
        }),
        b.actionDelFrame({daoname: th.daoUpdater, disabled: true, itemId: "DEL"}),
        b.actionRec({
          //NLS
          text: UtLang.t("Просмотр файла"),
          icon: "view",
          disabled: true,
          itemId: "VIEW",
          onExec: function(a) {
            Jc.showFrame({
              frame: "js/frame/file_viewer.gf", fileId: sgrid.getCurRec().get("fileStorage"), id: a.frame, replace: true
            });
          }})
      ]
    }
  });

  //
  sgrid.on("select", function(gr, rec) {
    if (!rec) return;
    Jc.getComponent(th, 'UPD').enable();
    Jc.getComponent(th, 'DEL').enable();
    Jc.getComponent(th, 'VIEW').enable();
  });
  //
  th.store.on('remove', function() {
    Jc.getComponent(th, 'UPD').disable();
    Jc.getComponent(th, 'VIEW').disable();
    Jc.getComponent(th, 'DEL').disable();
  });

  //
  th.items = [
    b.pageheader(th.title, 'docum'),
    sgrid
  ];

</g:javascript>
