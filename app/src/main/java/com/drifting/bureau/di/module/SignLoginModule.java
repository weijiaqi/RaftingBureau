package com.drifting.bureau.di.module;

import com.drifting.bureau.mvp.contract.SignLoginContract;
import com.drifting.bureau.mvp.model.SignLoginModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SignLoginModule {

    @Binds
    abstract SignLoginContract.Model bindSignLoginModel(SignLoginModel model);
}
