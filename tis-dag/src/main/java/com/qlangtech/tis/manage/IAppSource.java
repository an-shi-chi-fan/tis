/**
 * Copyright (c) 2020 QingLang, Inc. <baisui@qlangtech.com>
 * <p>
 * This program is free software: you can use, redistribute, and/or modify
 * it under the terms of the GNU Affero General Public License, version 3
 * or later ("AGPL"), as published by the Free Software Foundation.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.qlangtech.tis.manage;

import com.alibaba.citrus.turbine.Context;
import com.qlangtech.tis.extension.Describable;
import com.qlangtech.tis.extension.Descriptor;
import com.qlangtech.tis.plugin.ds.ColumnMetaData;
import com.qlangtech.tis.runtime.module.misc.IMessageHandler;
import com.qlangtech.tis.sql.parser.er.IPrimaryTabFinder;
import com.qlangtech.tis.sql.parser.tuple.creator.EntityName;

import java.util.List;

/**
 * 索引实例Srouce， 支持单表、dataflow
 *
 * @author 百岁（baisui@qlangtech.com）
 * @date 2021-03-31 11:16
 */
public interface IAppSource extends Describable<IAppSource> {

    List<ColumnMetaData> reflectCols();

    /**
     * 触发全量索引构建之前进行校验
     */
    boolean triggerFullIndexSwapeValidate(IMessageHandler msgHandler, Context ctx);

    /**
     * 全量构建流程中取得最终构建实体
     *
     * @return
     */
    EntityName getTargetEntity();

    IPrimaryTabFinder getPrimaryTabFinder();

    default Descriptor<IAppSource> getDescriptor() {
        throw new UnsupportedOperationException();
    }

}