package com.datn.finhome;

import com.google.firebase.database.DatabaseError;

public interface IAfterGetAllObject {
   void iAfterGetAllObject(Object obj);
   void onError(DatabaseError error);
}
