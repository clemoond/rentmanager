<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>
    <%@include file="/WEB-INF/views/common/head.jsp" %>

        <body class="hold-transition skin-blue sidebar-mini">
            <div class="wrapper">

                <%@ include file="/WEB-INF/views/common/header.jsp" %>
                    <!-- Left side column. contains the logo and sidebar -->
                    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

                        <!-- Content Wrapper. Contains page content -->
                        <div class="content-wrapper">
                            <!-- Content Header (Page header) -->
                            <section class="content-header">
                                <h1>
                                    Voitures
                                </h1>
                            </section>

                            <!-- Main content -->
                            <section class="content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <!-- Horizontal Form -->
                                        <div class="box">
                                            <!-- form start -->
                                            <form class="form-horizontal" method="post"
                                                action="${pageContext.request.contextPath}/vehicles/update">
                                                <div class="box-body">
                                                    <div class="form-group">
                                                        <div class="col-sm-10">
                                                            <input type="hidden" class="form-control" id="id" name="id"
                                                                value="${vehicle.id}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="constructeur" class="col-sm-2 control-label">Marque</label>
                                                        <div class="col-sm-10">
                                                            <input type="text" class="form-control" id="constructeur"
                                                                name="constructeur" placeholder="Constructeur" value="${vehicle.constructeur}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="modele" class="col-sm-2 control-label">Mod&egravele</label>
                                                        <div class="col-sm-10">
                                                            <input type="text" class="form-control" id="modele" name="modele" placeholder="Modele" value="${vehicle.modele}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="nb_places" class="col-sm-2 control-label">Nombre de places</label>

                                                        <div class="col-sm-10">
                                                            <input type="text" class="form-control" id="nb_places" name="nb_places" min="2" max="9" placeholder="Nb_places" value="${vehicle.nb_places}">
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- /.box-body -->
                                                <div class="box-footer">
                                                    <button type="submit"
                                                        class="btn btn-info pull-right">Accepter les modifications</button>
                                                </div>
                                                <!-- /.box-footer -->
                                            </form>
                                        </div>
                                        <!-- /.box -->
                                    </div>
                                    <!-- /.col -->
                                </div>
                            </section>
                            <!-- /.content -->
                        </div>

                        <%@ include file="/WEB-INF/views/common/footer.jsp" %>
            </div>
            <!-- ./wrapper -->

            <%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
        </body>

    </html>