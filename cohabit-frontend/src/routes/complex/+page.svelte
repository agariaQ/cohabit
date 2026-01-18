<script>
    import { onMount } from 'svelte';
    import { apiCall } from '$lib/api';
    import { user } from '$lib/auth';

    let complexes = [];
    let loading = true;

    onMount(async () => {
        try {
            complexes = await apiCall('complex', '/api/v1/catalog/complexes');
        } catch (e) {
            console.error(e);
        } finally {
            loading = false;
        }
    });
</script>

<div class="container mx-auto px-4 py-12 min-h-screen">

    <div class="flex flex-col md:flex-row justify-between items-center mb-10 gap-4">
        <div>
            <h1 class="text-3xl font-extrabold text-slate-900 tracking-tight">Wohnanlagen in Wien</h1>
            <p class="text-slate-500 mt-1">Entdecke aktuelle Angebote und Bewertungen.</p>
        </div>

        {#if $user && $user.roles && $user.roles.includes('VENDOR')}
            <a href="/vendor/dashboard"
               class="inline-flex items-center px-5 py-2.5 text-sm font-bold text-white bg-amber-500 rounded-lg hover:bg-amber-600 transition-colors shadow-sm">
                <span>🛠 Anbieter-Dashboard</span>
            </a>
        {/if}
    </div>

    {#if loading}
        <div class="flex flex-col items-center justify-center py-20 space-y-4">
            <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
            <span class="text-slate-400 font-medium">Lade Katalog...</span>
        </div>
    {:else if complexes.length === 0}
        <div class="text-center py-20 bg-white rounded-2xl border border-dashed border-slate-300">
            <p class="text-slate-500 text-lg">Aktuell sind keine Wohnanlagen gelistet.</p>
        </div>
    {:else}
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {#each complexes as complex}
                <a href="/src/routes/complex/{complex.id}" class="group bg-white rounded-2xl border border-slate-200 overflow-hidden hover:shadow-xl hover:border-blue-200 transition-all duration-300 flex flex-col h-full">

                    <div class="relative h-48 bg-slate-100 overflow-hidden">
                        {#if complex.imageUrls?.[0]}
                            <img src={complex.imageUrls[0]}
                                 alt={complex.name}
                                 class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500" />
                        {:else}
                            <div class="flex flex-col items-center justify-center h-full text-slate-400">
                                <span class="text-2xl mb-1">🏠</span>
                                <span class="text-xs font-medium">Kein Bild</span>
                            </div>
                        {/if}

                        {#if complex.averageRating}
                            <div class="absolute top-3 right-3 bg-white/95 backdrop-blur-sm px-2.5 py-1 rounded-full text-xs font-bold text-slate-800 shadow-sm flex items-center gap-1">
                                <span class="text-yellow-500">⭐</span> {complex.averageRating.toFixed(1)}
                            </div>
                        {/if}
                    </div>

                    <div class="p-5 flex flex-col flex-grow">
                        <div class="flex-grow">
                            <h3 class="text-lg font-bold text-slate-800 mb-1 group-hover:text-blue-600 transition-colors line-clamp-1">
                                {complex.name}
                            </h3>
                            <p class="text-slate-500 text-sm flex items-center gap-1.5 mb-3">
                                📍 <span class="truncate">{complex.zipCode} {complex.district}</span>
                            </p>
                        </div>

                        <div class="pt-4 border-t border-slate-100 flex justify-between items-center mt-2">
                            <span class="text-xs font-semibold text-slate-400 uppercase tracking-wide">Details ansehen</span>
                            <div class="w-8 h-8 rounded-full bg-slate-50 flex items-center justify-center group-hover:bg-blue-50 group-hover:text-blue-600 transition-colors">
                                <span class="font-bold text-lg leading-none">&rarr;</span>
                            </div>
                        </div>
                    </div>
                </a>
            {/each}
        </div>
    {/if}
</div>