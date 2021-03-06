package com.spdb.scenicrouteplanner.database;

import android.content.Context;

import com.spdb.scenicrouteplanner.lib.OSM.OSMClassLib;

import static com.spdb.scenicrouteplanner.database.RoutesDbContract.*;

import org.spatialite.database.SQLiteDatabase;
import org.spatialite.database.SQLiteOpenHelper;

// Na razie nieużywane.
class RoutesDbHelper extends SQLiteOpenHelper
{
    // ==============================
    // Private fields
    // ==============================
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Routes.db";

    private static final String SQL_INIT_SPATIAL_META_DATA = " SELECT InitSpatialMetaData(1)";
    private static final String SQL_CREATE_NODES_TABLE = "CREATE TABLE " + NodesTable.TABLE_NAME +
            "( " + NodesTable._ID + " INTEGER PRIMARY KEY, " +
            NodesTable.GEOMETRY_COL_NAME + " GEOMETRY UNIQUE);";
    private static final String SQL_CREATE_EDGES_TABLE = "CREATE TABLE " + EdgesTable.TABLE_NAME +
            "( " + EdgesTable._ID + " INTEGER PRIMARY KEY, " +
            EdgesTable.WAY_ID_COL_NAME + " INTEGER, " +
            EdgesTable.START_NODE_ID_COL_NAME + " INTEGER, " +
            EdgesTable.END_NODE_ID_COL_NAME + " INTEGER, " +
            EdgesTable.IS_TOUR_ROUTE_COL_NAME + " INTEGER, " +
            EdgesTable.GEOMETRY_COL_NAME + " UNIQUE, " +
            "CONSTRAINT " + EdgesTable.WAY_ID_FOREIGN_KEY_CONSTRAINT_NAME + " FOREIGN KEY " +
            "(" + EdgesTable.WAY_ID_COL_NAME + ") " +
            "REFERENCES " + WaysTable.TABLE_NAME + "(" + WaysTable._ID + "), " +
            /*"CONSTRAINT " + EdgesTable.START_NODE_ID_FOREIGN_KEY_CONSTRAINT_NAME + " FOREIGN KEY " +
            "(" + EdgesTable.START_NODE_ID_COL_NAME + ") " +
            "REFERENCES " + NodesTable.TABLE_NAME + "(" + NodesTable._ID + "), " +
            "CONSTRAINT " + EdgesTable.END_NODE_ID_FOREIGN_KEY_CONSTRAINT_NAME + " FOREIGN KEY " +
            "(" + EdgesTable.END_NODE_ID_COL_NAME + ") " +
            "REFERENCES " + NodesTable.TABLE_NAME + "(" + NodesTable._ID + "), " +*/
            "CONSTRAINT " + EdgesTable.IS_TOUR_ROUTE_CHECK_NAME + " CHECK " +
            "(" + EdgesTable.IS_TOUR_ROUTE_COL_NAME + " IN (0, 1))" + ");";
    private static final String SQL_CREATE_WAYS_TABLE = "CREATE TABLE " + WaysTable.TABLE_NAME +
            "( " + WaysTable._ID + " INTEGER PRIMARY KEY, " +
            WaysTable.WAY_TYPE_COL_NAME + " TEXT, " +
            WaysTable.IS_SCENIC_ROUTE_COL_NAME + " INTEGER, " +
            WaysTable.MAX_SPEED_COL_NAME + " INTEGER, " +
            "CONSTRAINT " + WaysTable.WAY_TYPE_CHECK_NAME + " CHECK " +
            "(" + WaysTable.WAY_TYPE_COL_NAME + " IN (" + OSMClassLib.WayType.getWayTypesListText() + ")), " +
            "CONSTRAINT " + WaysTable.IS_SCENIC_ROUTE_CHECK_NAME + " CHECK " +
            "(" + WaysTable.IS_SCENIC_ROUTE_COL_NAME + " IN (0, 1))" + ");";


    // ==============================
    // Constructors
    // ==============================
    RoutesDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ==============================
    // Override SQLiteOpenHelper
    // ==============================
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Ze wzgledu na problem generacji przestrzennych metadanych - zakomentowane.
        // Baza dostarczana statycznie.
        /*
        db.rawQuery(SQL_INIT_SPATIAL_META_DATA, null);
        /*db.execSQL(SQL_CREATE_NODES_TABLE);
        db.rawQuery(recoverGeometryColumn(NodesTable.TABLE_NAME, NodesTable.GEOMETRY_COL_NAME,
                OSM_SRID, GeometryType.POINT.getName()), null);
        db.execSQL(SQL_CREATE_EDGES_TABLE);
        db.rawQuery(recoverGeometryColumn(EdgesTable.TABLE_NAME, EdgesTable.GEOMETRY_COL_NAME,
                OSM_SRID, GeometryType.MULTIPOINT.getName()), null);
        db.execSQL(SQL_CREATE_WAYS_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Ze wzgledu na problem generacji przestrzennych metadanych - zakomentowane.
        // Baza dostarczana w sposób statyczny.
        /*
        if (newVersion > oldVersion)
        {
            db.execSQL(getSqlDeleteTable(NodesTable.TABLE_NAME));
            db.execSQL(getSqlDeleteTable(EdgesTable.TABLE_NAME));
            db.execSQL(getSqlDeleteTable(WaysTable.TABLE_NAME));
            db.execSQL(getSqlDeleteTable(GEOMETRY_COLUMNS_TABLE_NAME));
            db.execSQL(getSqlDeleteTable(SPATIAL_REF_SYS_TABLE_NAME));

            onCreate(db);
        }*/
    }

    // ==============================
    // Private methods
    // ==============================

    private static String recoverGeometryColumn(String tableName, String geometryColName, int srid,
                                                String geomType)
    {
        return String.format("SELECT RecoverGeometryColumn('%s', '%s', %d, '%s')",
                tableName, geometryColName, srid, geomType);
    }

    private static String getSqlDeleteTable(String tableName)
    {
        return String.format("DROP TABLE IF EXISTS %s;", tableName);
    }
}
