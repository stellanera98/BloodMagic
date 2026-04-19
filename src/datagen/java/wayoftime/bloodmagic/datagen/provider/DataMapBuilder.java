package wayoftime.bloodmagic.datagen.provider;

import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.function.Function;

public interface DataMapBuilder<R, T> extends Function<DataMapType<R, T>, DataMapProvider.Builder<T, R>> {}
