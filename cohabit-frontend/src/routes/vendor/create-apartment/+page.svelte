<script>
    import { apiCall } from '$lib/api';
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';

    let complexes = [];

    let dto = {
        complexId: '',
        title: '',
        description: '',
        doorNumber: '',
        floor: 0,
        sizeSqm: 0
    };

    onMount(async () => {
        complexes = await apiCall('complex', '/api/v1/catalog/complexes');
        if (complexes.length > 0) dto.complexId = complexes[0].id;
    });

    async function submit() {
        try {
            await apiCall('complex', '/api/v1/catalog/apartments', 'POST', dto);
            alert('Wohnung hinzugefügt!');
            goto('/vendor/dashboard');
        } catch (e) {
            alert('Fehler: ' + e.message);
        }
    }
</script>

<div class="container">
    <h2>Neue Wohnung inserieren</h2>
    <form on:submit|preventDefault={submit}>

        <div class="form-group">
            <label>Gehört zu welcher Anlage?</label>
            <select bind:value={dto.complexId} required>
                {#each complexes as c}
                    <option value={c.id}>{c.name} ({c.zipCode})</option>
                {/each}
            </select>
        </div>

        <div class="form-group">
            <label>Titel des Inserats</label>
            <input type="text" bind:value={dto.title} required placeholder="Helle 2-Zimmer Wohnung..." />
        </div>

        <div class="form-group">
            <label>Beschreibung</label>
            <textarea bind:value={dto.description} rows="4"></textarea>
        </div>

        <div class="row">
            <div class="form-group">
                <label>Top Nr.</label>
                <input type="text" bind:value={dto.doorNumber} required />
            </div>
            <div class="form-group">
                <label>Stockwerk</label>
                <input type="number" bind:value={dto.floor} required />
            </div>
            <div class="form-group">
                <label>Größe (m²)</label>
                <input type="number" step="0.1" bind:value={dto.sizeSqm} required />
            </div>
        </div>

        <button type="submit" class="btn-save">Inserat erstellen</button>
    </form>
</div>

<style>
    .container { max-width: 600px; margin: 40px auto; background: white; padding: 30px; border-radius: 12px; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 0.9rem; }
    input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 6px; }
    .row { display: flex; gap: 15px; }
    .btn-save { width: 100%; background: #27ae60; color: white; padding: 12px; border: none; border-radius: 6px; font-weight: bold; cursor: pointer; margin-top: 10px;}
</style>